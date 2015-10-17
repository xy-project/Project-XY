package com.projextxy.core.blocks.item

import java.util

import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{ItemBlock, ItemStack}
import net.minecraft.util.EnumChatFormatting

class ItemBlockColorContainer(block: Block) extends ItemBlock(block) {
  setHasSubtypes(true)
  setMaxDamage(0)


  override def getMetadata(meta: Int): Int = meta

  override def addInformation(stack: ItemStack, player: EntityPlayer, _list: util.List[_], par4: Boolean) = {
    val list = _list.asInstanceOf[util.List[String]]

    if (stack.stackTagCompound != null) {
      list.add(s"${EnumChatFormatting.RED}Red: ${stack.stackTagCompound.getInteger("r")}")
      list.add(s"${EnumChatFormatting.GREEN}Green: ${stack.stackTagCompound.getInteger("g")}")
      list.add(s"${EnumChatFormatting.BLUE}Blue: ${stack.stackTagCompound.getInteger("b")}")
    }
  }
}
