package com.projextxy.core.blocks.glow

import java.util

import com.projextxy.core.blocks.BlockXy
import com.projextxy.core.blocks.traits.{ColorMultiplier, TConnectedTextureBlock}
import com.projextxy.core.client.render.block.RenderSimpleGlow
import com.projextxy.core.reference.{MCColors, ModColors}
import com.projextxy.core.{ProjectXYCore, ProjectXYCoreProxy}
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EnumCreatureType
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess

class BlockXyGlow(mat: Material) extends BlockXy(mat) {
  setHardness(1.0F)
  var colors: List[MCColors] = ModColors.xyColors


  override def getRenderType: Int = RenderSimpleGlow.renderId

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
  
  def getAnimationIcon():IIcon = BlockXyGlow.animationIcon
}

object BlockXyGlow {
  def animationIcon: IIcon = ProjectXYCoreProxy.animationFx.texture

  def apply: BlockXyGlow = new BlockXyGlow(Material.rock)

  def apply(material: Material): BlockXyGlow = new BlockXyGlow(material)


  def apply(name: String, mat: Material, colorMultiplier: Boolean): BlockXyGlow = apply(name, mat, None, colorMultiplier)

  def apply(name: String): BlockXyGlow = apply(name, Material.rock, None, colorMultiplier = false)

  def apply(name: String, material: Material, colors: Option[List[MCColors]], colorMultiplier: Boolean): BlockXyGlow = {
    val block: BlockXyGlow = if (colorMultiplier) new BlockXyGlow(material) with ColorMultiplier else new BlockXyGlow(material)
    block.setBlockName(name)
    if (colors.isDefined)
      block.colors = colors.get
    block
  }

  def apply(name: String, colorMultiplier: Boolean): BlockXyGlow = apply(name, Material.rock, None, colorMultiplier)
}

object BlockXyGlowConnected {
  def apply(name: String, texture: String, mat: Material, option: Option[List[MCColors]]): BlockXyGlow = apply(name, texture, mat, option, colorMultiplier = false, renderBlockIcon = false)

  def apply(name: String, texture: String, mat: Material): BlockXyGlow = apply(name, texture, mat, None, colorMultiplier = false, renderBlockIcon = false)

  def apply(name: String, texture: String, mat: Material, colorMultiplier: Boolean): BlockXyGlow = apply(name, texture, mat, None, colorMultiplier, renderBlockIcon = false)

  def apply(name: String, texture: String, mat: Material, option: Option[List[MCColors]], colorMultiplier: Boolean, renderBlockIcon: Boolean): BlockXyGlow = {
    val block = if (colorMultiplier)
      new BlockXyGlow(mat) with TConnectedTextureBlock with ColorMultiplier {
        val connectedFolder = texture
        val renderBlockTexture = renderBlockIcon
      }
    else new BlockXyGlow(mat) with TConnectedTextureBlock {
      val connectedFolder = texture
      val renderBlockTexture = renderBlockIcon
    }
    block.setBlockName(name)
    if (option.isDefined)
      block.colors = option.get
    block
  }

  def apply(name: String, texture: String): BlockXyGlow = apply(name, texture, Material.rock, None, colorMultiplier = false, renderBlockIcon = false)

  def apply(name: String, texture: String, colorMultiplier: Boolean): BlockXyGlow = apply(name, texture, Material.rock, None, colorMultiplier, renderBlockIcon = false)

  def apply(name: String, texture: String, option: Option[List[MCColors]], colorMultiplier: Boolean): BlockXyGlow = apply(name, texture, Material.rock, option, colorMultiplier, renderBlockIcon = false)
}
