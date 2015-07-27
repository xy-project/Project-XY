package com.projextxy.core.blocks

import java.util

import com.projextxy.core.ProjectXYCore
import com.projextxy.core.blocks.traits.ColorMultiplier
import com.projextxy.core.reference.{MCColors, ModColors}
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.world.IBlockAccess

class BlockXyColored extends BlockXy(Material.rock) with ColorMultiplier {
  setHardness(1.0F)

  var colors: List[MCColors] = ModColors.xyColors

  override def getColor(world: IBlockAccess, x: Int, y: Int, z: Int): Int = colors(world.getBlockMetadata(x, y, z)).rgb

  override def getColor(meta: Int): Int = colors(meta).rgb

  override def registerBlockIcons(reg: IIconRegister): Unit = blockIcon = reg.registerIcon(s"${ProjectXYCore.MOD_ID}:$textureName")

  override def getSubBlocks(item: Item, tab: CreativeTabs, _list: util.List[_]): Unit = {
    val list = _list.asInstanceOf[util.List[ItemStack]]
    for (x <- colors.indices)
      list.add(new ItemStack(this, 1, x))
  }

  override def damageDropped(meta: Int): Int = meta
}
