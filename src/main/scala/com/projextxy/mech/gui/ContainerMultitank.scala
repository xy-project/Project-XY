package com.projextxy.mech.gui

import codechicken.lib.inventory.ContainerExtended
import com.projextxy.mech.multiblock.MultiTank
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Slot

class ContainerMultitank(inventory: InventoryPlayer, multiTank: MultiTank) extends ContainerExtended {
  addSlotToContainer(new Slot(multiTank, 0, 98, 50))
  addSlotToContainer(new Slot(multiTank, 1, 152, 50))
  bindPlayerInventory(inventory, 8, 104)
}
