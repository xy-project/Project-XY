package com.projextxy.core.blocks

import java.util

import com.projextxy.core.client.CTRegistry
import com.projextxy.core.client.render.block.RenderConnectedTexture
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.util.IIcon
import net.minecraft.world.{IBlockAccess, World}

case class BlockXyDecor(textureFolders: List[String], mat: Material, isGlass: Boolean) extends BlockXy(mat) {
  setHardness(1.0F)

  override def registerBlockIcons(reg: IIconRegister): Unit = {}

  override def getSubBlocks(item: Item, tab: CreativeTabs, _list: util.List[_]): Unit = {
    val list = _list.asInstanceOf[util.List[ItemStack]]
    for (i <- textureFolders.indices)
      list.add(new ItemStack(this, 1, i))
  }

  override def getIcon(side: Int, meta: Int): IIcon = CTRegistry.getTexture(textureFolders(meta))

  override def getRenderType: Int = RenderConnectedTexture.renderType

  override def isOpaqueCube: Boolean = !isGlass

  override def getRenderBlockPass: Int = if (isGlass) 1 else 0

  override def shouldSideBeRendered(world: IBlockAccess, x: Int, y: Int, z: Int, side: Int): Boolean = if (world.getBlock(x, y, z) == this) false else super.shouldSideBeRendered(world, x, y, z, side)

  override def isBlockNormalCube: Boolean = !isGlass

  override def renderAsNormalBlock(): Boolean = false

  override def canPlaceTorchOnTop(world: World, x: Int, y: Int, z: Int): Boolean = !isGlass
}