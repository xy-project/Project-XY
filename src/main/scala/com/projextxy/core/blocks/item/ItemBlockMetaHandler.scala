package com.projextxy.core.blocks.item

import net.minecraft.block.Block
import net.minecraft.item.{ItemStack, ItemBlock}

class ItemBlockMetaHandler(block: Block) extends ItemBlock(block) {
  setHasSubtypes(true)
  setMaxDamage(0)


  override def getMetadata(meta : Int): Int = meta

  override def getUnlocalizedName(stack: ItemStack): String = s"${super.getUnlocalizedName(stack)}|${stack.getItemDamage}"
}
