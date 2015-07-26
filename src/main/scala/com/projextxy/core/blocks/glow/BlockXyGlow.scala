package com.projextxy.core.blocks.glow

import java.util

import com.projextxy.core.blocks.BlockXy
import com.projextxy.core.reference.MCColors
import com.projextxy.core.{ProjectXYCore, ProjectXYCoreProxy}
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EnumCreatureType
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import net.minecraftforge.client.ForgeHooksClient

class BlockXyGlow(mat: Material, renderType: Int) extends BlockXy(mat) {
  var colors: List[MCColors] = MCColors.VALID_COLORS.toList

  override def getRenderType: Int = renderType

  override def getSubBlocks(item: Item, tabs: CreativeTabs, _list: util.List[_]) = {
    val list = _list.asInstanceOf[util.List[ItemStack]]
    for (colorIndex <- colors.indices) {
      list.add(new ItemStack(this, 1, colorIndex))
    }
  }

  override def registerBlockIcons(iconRegister: IIconRegister) = {
    blockIcon = iconRegister.registerIcon(s"${ProjectXYCore.MOD_ID}:overlay/$getTextureName")
  }

  override def damageDropped(meta: Int): Int = meta

  override def canCreatureSpawn(creatureType: EnumCreatureType, world: IBlockAccess, x: Int, y: Int, z: Int): Boolean = false

  def getColor(meta: Int): Int = colors(meta).rgb

  def getColor(world: IBlockAccess, x: Int, y: Int, z: Int): Int = colors(world.getBlockMetadata(x, y, z)).rgb

  def getBrightness(world: IBlockAccess, x: Int, y: Int, z: Int): Int = 220

  def getBrightness(meta: Int): Int = 220
}

object BlockXyGlow {
  def baseIcon: IIcon = ProjectXYCoreProxy.animationFx.texture
}
