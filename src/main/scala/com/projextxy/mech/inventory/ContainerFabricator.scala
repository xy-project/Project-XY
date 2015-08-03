package com.projextxy.mech.inventory

import codechicken.lib.inventory.{SlotDummyOutput, ContainerExtended, SlotDummy}
import com.projextxy.mech.tile.TileFabricator
import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack

class ContainerFabricator(inventoryPlayer: InventoryPlayer, tile: TileFabricator) extends ContainerExtended {
  for (row <- 0 until 3)
    for (column <- 0 until 3)
      addSlotToContainer(new SlotDummy(tile, row * 3 + column, 8 + column * 18, 30 + row * 18, 1) {
        override def canTakeStack(p_82869_1_ : EntityPlayer): Boolean = false
      })
  addSlotToContainer(new SlotDummyOutput(tile, 9, 79, 48) {
    override def slotClick(container: ContainerExtended, player: EntityPlayer, button: Int, modifier: Int): ItemStack = {
      //Clears crafting in one click
      if (button == 1)
        for (i <- 0 until 9)
          tile.setInventorySlotContents(i, null)
      super.slotClick(container, player, button, modifier)
    }
    override def canTakeStack(p_82869_1_ : EntityPlayer): Boolean = false
  })
  for (row <- 0 until 3)
    for (column <- 0 until 3)
      addSlotToContainer(new Slot(tile, row * 3 + column + 10, 116 + column * 18, 30 + row * 18))
  bindPlayerInventory(inventoryPlayer, 8, 104)

  override def doMergeStackAreas(slotIndex: Int, stack: ItemStack): Boolean = {
    if (slotIndex < 19) {
      return mergeItemStack(stack, 19, 55, true)
    }
    mergeItemStack(stack, 10, 19, false)
  }
}
