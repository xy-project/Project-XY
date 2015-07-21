package com.projextxy.mech

import java.util

import codechicken.lib.vec.{Vector3, BlockCoord}
import codechicken.multipart.{TMultiPart, TItemMultiPart}
import com.projextxy.core.ProjectXYCore
import com.projextxy.mech.fmp.{NodePart, ReceiverPart, ProviderPart}
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.world.World

class ItemXynergy extends TItemMultiPart {
  setUnlocalizedName("itemXynergy")
  setHasSubtypes(true)
  setCreativeTab(ProjectXYCore.tabItem)

  override def newPart(item: ItemStack, player: EntityPlayer, world: World, pos: BlockCoord, side: Int, vhit: Vector3): TMultiPart = {
    item.getItemDamage match {
      case 0 => new ReceiverPart
      case 1 => new ProviderPart
      case 2 => new NodePart
    }
  }

  override def getSubItems(item: Item, tabs: CreativeTabs, _list: util.List[_]) {
    val list = _list.asInstanceOf[util.List[ItemStack]]

    list.add(new ItemStack(this, 1, 0))
    list.add(new ItemStack(this, 1, 1))
    list.add(new ItemStack(this, 1, 2))
  }
}
