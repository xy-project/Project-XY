package com.projextxy.mech.tile

import codechicken.lib.data.{MCDataInput, MCDataOutput}
import codechicken.lib.inventory.{InventoryRange, InventoryUtils}
import com.projextxy.core.tile.traits.TMachineTile
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.{Container, ISidedInventory, InventoryCrafting}
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.{CraftingManager, IRecipe}
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.ForgeDirection

import scala.collection.JavaConversions._
import scala.util.control.Breaks._

class TileFabricator extends TMachineTile with ISidedInventory {
  var currentRecipe: IRecipe = null

  //Standard TileEntity stuff
  override def validate(): Unit = {
    inv_changed = true
    super.validate()
  }

  override def updateEntity(): Unit = {
    if (!worldObj.isRemote) {
      if (inv_changed) {
        inv_changed = false
        loadRecipe()
      }
      craft()
    } else {
    }
  }

  def craft(result: ItemStack, items: Array[RecipeInvStack], crafting: InventoryCrafting): Unit = {
    for (i <- 0 until 9) {
      val stack = crafting.getStackInSlot(i)
      if (stack != null) {
        crafting.decrStackSize(i, 1)
        if (stack.getItem.hasContainerItem(stack)) {
          var container = stack.getItem.getContainerItem(stack)
          if (container != null) {
            if (container.isItemStackDamageable && (container.getItemDamage > container.getMaxDamage)) {
              container = null
            }
            crafting.setInventorySlotContents(i, container)
          }
        }
      }
    }
  }


  def updateInventories(items: Array[RecipeInvStack], result: ItemStack, crafting: InventoryCrafting): Unit = {
    for (invstack <- items) {
      if (invstack != null) {
        val stack = invstack.access.inv.getStackInSlot(invstack.slot)
        stack.stackSize -= 1
        if (stack.stackSize < 1)
          invstack.access.inv.setInventorySlotContents(invstack.slot, null)
        else
          invstack.access.inv.setInventorySlotContents(invstack.slot, stack)
      }
    }
    for (i <- 0 until 9) {
      val stack = items(i)
      val container = crafting.getStackInSlot(i)
      if (container != null && stack != null)
        InventoryUtils.insertItem(stack.access.inv, container, false)
    }
    InventoryUtils.insertItem(this, result, false)
  }

  def craft(): Unit = {
    val result = getStackInSlot(OUTPUT_SLOT_INDEX)
    if (result == null)
      return
    val crafting = getCrafted()
    val items = findRecipeItems(crafting, result)
    if (items == null)
      return
    replaceCurrentCrafingRecipe(crafting, items)
    craft(result, items, crafting)
    if (!shouldMergeStacks(items, crafting, result))
      return
    updateInventories(items, result, crafting)
  }

  def shouldMergeStacks(items: Array[RecipeInvStack], crafting: InventoryCrafting, out: ItemStack): Boolean = {
    for (i <- 0 until 9) {
      val stack = items(i)
      val container = crafting.getStackInSlot(i)
      if (stack != null && container != null && InventoryUtils.insertItem(stack.access, container, true) > 0)
        return false
    }
    if (InventoryUtils.insertItem(new InventoryRange(this, 0), out, true) > 0)
      return false
    true
  }


  def replaceCurrentCrafingRecipe(inventoryCrafting: InventoryCrafting, stacks: Array[RecipeInvStack]): Unit = {
    for (i <- 0 until 9)
      if (stacks(i) != null)
        inventoryCrafting.setInventorySlotContents(i, InventoryUtils.copyStack(stacks(i).stack, 1))
  }

  def findRecipeItems(ingredients: InventoryCrafting, out: ItemStack): Array[RecipeInvStack] = {
    val items = Array.fill[RecipeInvStack](9)(null)
    val foundItems = Array.fill[Boolean](9)(false)
    for (i <- 0 until 9)
      foundItems(i) = ingredients.getStackInSlot(i) == null
    findRecipeItems(items, foundItems, new InventoryRange(this, 0), ingredients, out)
    for (side <- ForgeDirection.VALID_DIRECTIONS) {
      val inv = InventoryUtils.getInventory(worldObj, xCoord + side.offsetX, yCoord + side.offsetY, zCoord + side.offsetZ)
      if (inv != null) {
        val access = new InventoryRange(inv, side.getOpposite.ordinal)
        findRecipeItems(items, foundItems, access, ingredients, out)
      }
    }
    for (i <- 0 until 9)
      if (!foundItems(i))
        return null
    items
  }

  def testCraft(ingredients: InventoryCrafting, out: ItemStack, stack: ItemStack, slot: Int): Boolean = {
    val rstack = ingredients.getStackInSlot(slot)
    ingredients.setInventorySlotContents(slot, stack)
    var valid = false
    if (currentRecipe.matches(ingredients, worldObj)) {
      valid = InventoryUtils.areStacksIdentical(out, currentRecipe.getCraftingResult(ingredients))
    }
    ingredients.setInventorySlotContents(slot, rstack)
    valid
  }

  def findRecipeItems(items: Array[RecipeInvStack], foundItems: Array[Boolean], access: InventoryRange, ingredients: InventoryCrafting, out: ItemStack): Unit = {
    for (slot <- 0 until access.lastSlot()) {
      var stack = InventoryUtils.getExtractableStack(access.inv, slot)
      if (stack != null && stack.stackSize >= 1) {
        stack = stack.copy
        breakable {
          for (islot <- 0 until 9) {
            if (!foundItems(islot)) {
              if (testCraft(ingredients, out, stack, islot)) {
                items(islot) = new RecipeInvStack(access, slot)
                foundItems(islot) = true
                stack.stackSize -= 1
                if (stack.stackSize == 0)
                  break()
              }
            }
          }
        }
      }
    }
  }

  def loadRecipe(): Unit = {
    val crafted = getCrafted()
    val list = CraftingManager.getInstance().getRecipeList.asInstanceOf[java.util.List[IRecipe]]
    val recipe = list.filter(p => p.matches(crafted, worldObj))
    if (recipe.isEmpty) {
      setInventorySlotContents(9, null)
      return
    }
    currentRecipe = recipe.head
    setInventorySlotContents(9, recipe.head.getRecipeOutput)
  }

  def getCrafted(): InventoryCrafting = {
    val inv = new InventoryCrafting(new FabricatorCraftingInventory, 3, 3)
    for (i <- CRAFTING_INVENTORY_RANGE) inv.setInventorySlotContents(i, getStackInSlot(i))
    inv
  }

  //Packets and NBT saving
  override def saveNBT(compound: NBTTagCompound): Unit = {
    compound.setTag("items", InventoryUtils.writeItemStacksToTag(inv))
  }

  override def receivePacket(in: MCDataInput): Unit = {}

  override def writeToPacket(out: MCDataOutput): Unit = {}

  override def readNBT(compound: NBTTagCompound): Unit = {
    InventoryUtils.readItemStacksFromTag(inv, compound.getTagList("items", 10))
  }

  //Inventory
  var inv_changed = false
  val INVENTORY_SIZE = 19
  val OUTPUT_SLOT_INDEX = 9
  val CRAFTING_INVENTORY_RANGE = 0 until 9
  val inv = Array.fill[ItemStack](INVENTORY_SIZE)(null)

  override def decrStackSize(slotIndex: Int, amt: Int): ItemStack = InventoryUtils.decrStackSize(this, slotIndex, amt)

  override def closeInventory(): Unit = {}

  override def getSizeInventory: Int = INVENTORY_SIZE

  override def getInventoryStackLimit: Int = 64

  override def isItemValidForSlot(slotIndex: Int, stack: ItemStack): Boolean = true

  override def getStackInSlotOnClosing(slot: Int): ItemStack = InventoryUtils.getStackInSlotOnClosing(this, slot)

  override def openInventory(): Unit = {}

  override def setInventorySlotContents(slotIndex: Int, stack: ItemStack): Unit = {
    inv(slotIndex) = stack
    onInventoryChanged()
  }

  private def onInventoryChanged() = inv_changed = true

  override def isUseableByPlayer(player: EntityPlayer): Boolean = true

  override def getStackInSlot(slot: Int): ItemStack = inv(slot)

  override def hasCustomInventoryName: Boolean = true

  override def getInventoryName: String = "container.Fabricator"

  override def canExtractItem(slotIndex: Int, stack: ItemStack, side: Int): Boolean = if (slotIndex < 10) false else true

  override def canInsertItem(slotIndex: Int, stack: ItemStack, side: Int): Boolean = if (slotIndex >= 10) true else false

  override def getAccessibleSlotsFromSide(side: Int): Array[Int] = (10 until INVENTORY_SIZE).toArray
}

class FabricatorCraftingInventory extends Container {
  override def canInteractWith(p_75145_1_ : EntityPlayer): Boolean = true
}

case class RecipeInvStack(access: InventoryRange, slot: Int) {
  val stack = access.inv.getStackInSlot(slot)
}
