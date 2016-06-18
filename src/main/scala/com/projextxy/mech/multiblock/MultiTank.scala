package com.projextxy.mech.multiblock

import codechicken.lib.data.{MCDataInput, MCDataOutput}
import codechicken.lib.inventory.InventoryUtils
import codechicken.lib.render.{CCRenderState, RenderUtils}
import codechicken.lib.vec.{BlockCoord, Cuboid6, CuboidCoord}
import com.projextxy.core.ProjectXYCore
import com.projextxy.core.handler.GuiHandler
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.{ChunkCoordIntPair, World}
import net.minecraftforge.fluids.IFluidContainerItem

case class MultiTank(worldExt: XYWE, chunkExt: XYCE) extends MultiBlock(worldExt, chunkExt) with TFluidMultiBlock with IInventory {
  def this(worldObj: World, location: ChunkCoordIntPair, airArea: CuboidCoord) = {
    this(
      XYWEI.getExtension(worldObj).asInstanceOf[XYWE],
      XYWEI.getExtension(worldObj).getChunkExtension(location.chunkXPos, location.chunkZPos).asInstanceOf[XYCE])
    area = airArea
    initTank()
  }

  val inv = Array.fill[ItemStack](2)(null)
  var area: CuboidCoord = null


  /**
   * @param blockPos the position of the block being clicked
   * @param player the player
   * @return a int value corresponding to the type of action that should be completed after the block has been activated, 0 - nothing, 1 - finish, 2 - continue or finish at end
   */
  override def onActivated(blockPos: BlockCoord, player: EntityPlayer): Int = {
    world.getTileEntity(blockPos.x, blockPos.y, blockPos.z) match {
      case tile: TileMultiBlock => if (tile.formedMultiBlocks.size() > 1) return 2
        player.openGui(ProjectXYCore, GuiHandler.GuiIds.TANK.id, world, blockPos.x, blockPos.y, blockPos.z)
        return 1
      case _ =>
    }
    super.onActivated(blockPos, player)
  }

  override def getMultiBlockId: MultiBlockId = MultiBlockId.TANK


  override def writeToNBT(nbt: NBTTagCompound): Unit = {
    super.writeToNBT(nbt)
    nbt.setTag("tank", tank.toTag)
  }

  override def writeBlockCoords(out: NBTTagCompound): NBTTagCompound = {
    out.setIntArray("block_coords", area.intArray())
    out
  }

  override def readBlockCoords(in: NBTTagCompound): Unit = {
    inBlocks.clear()
    area = new CuboidCoord(in.getIntArray("block_coords"))
    for (x <- area.min.x - 1 to area.max.x + 1)
      for (y <- area.min.y - 1 to area.max.y + 1)
        for (z <- area.min.z - 1 to area.max.z + 1)
          addTile(new BlockCoord(x, y, z))
  }

  override def readFromDescriptionPacket(in: MCDataInput): Unit = {
    super.readFromDescriptionPacket(in)
    initTank()
    tank.read(in)
  }


  override def writeToDescriptionPacket(out: MCDataOutput): Unit = {
    tank.write(out)
  }

  override def readFromNBT(nbt: NBTTagCompound): Unit = {
    super.readFromNBT(nbt)
    initTank()
    tank.fromTag(nbt.getCompoundTag("tank"))
  }


  @SideOnly(Side.CLIENT) override
  def render(v: Float, i: Int): Unit = {
    CCRenderState.reset()
    val center = area.getCenter(new BlockCoord())
    CCRenderState.setBrightness(world, center.x, center.y, center.z)

    RenderUtils.renderFluidCuboid(tank.getFluid, new Cuboid6(area.min.x, area.min.y, area.min.z, area.max.x + 1, (((area.max.y + 1) - area.min.y) * (tank.c_ammount / tank.getCapacity.toDouble)) + area.min.y, area.max.z + 1), 1D, 1D)
  }

  def onInvChanged(): Unit = requestUpdate()

  override def decrStackSize(slot: Int, size: Int): ItemStack = InventoryUtils.decrStackSize(this, slot, size)

  override def closeInventory(): Unit = {}

  override def getSizeInventory: Int = inv.length

  override def getInventoryStackLimit: Int = 64

  override def markDirty(): Unit = chunkExt.chunk.setChunkModified()

  override def isItemValidForSlot(slot: Int, stack: ItemStack): Boolean = stack.getItem.isInstanceOf[IFluidContainerItem]

  override def getStackInSlotOnClosing(slot: Int): ItemStack = InventoryUtils.getStackInSlotOnClosing(this, slot)

  override def openInventory(): Unit = {}

  override def setInventorySlotContents(slot: Int, stack: ItemStack): Unit = {
    inv(slot) = stack
    onInvChanged()
  }

  override def isUseableByPlayer(p_70300_1_ : EntityPlayer): Boolean = true

  override def getStackInSlot(slot: Int): ItemStack = inv(slot)

  override def hasCustomInventoryName: Boolean = false

  override def getInventoryName: String = "Multi Tank"
}
