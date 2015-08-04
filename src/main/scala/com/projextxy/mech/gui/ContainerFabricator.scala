package com.projextxy.mech.gui

import codechicken.lib.inventory.{ContainerExtended, SlotDummy, SlotDummyOutput}
import codechicken.lib.packet.PacketCustom
import com.projextxy.mech.tile.TileFabricator
import cpw.mods.fml.relauncher.{Side, SideOnly}
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

  val REDSTONE_SIGNAL_CHANGE = 1

  override def doMergeStackAreas(slotIndex: Int, stack: ItemStack): Boolean = {
    if (slotIndex < 19) return mergeItemStack(stack, 19, 55, true)
    mergeItemStack(stack, 10, 19, false)
  }

  @SideOnly(Side.CLIENT)
  def handleGuiChange(selected_redstone_signal: Int): Unit = {
    tile.mode = selected_redstone_signal
    tile.getWorldObj.markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord)
    val packet = getPacket(2)
    packet.writeInt(REDSTONE_SIGNAL_CHANGE)
    packet.writeInt(selected_redstone_signal)
    packet.sendToServer()
  }

  override def handleServerPacket(packet: PacketCustom): Unit = {
    packet.readInt match {
      case REDSTONE_SIGNAL_CHANGE =>
        tile.mode = packet.readInt
        tile.getWorldObj.markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord)
      case _ =>
    }
  }
}
