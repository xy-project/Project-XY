package com.projextxy.core.blocks.glow

import java.util

import com.projextxy.core.blocks.BlockXy
import com.projextxy.core.blocks.BlockXy._
import com.projextxy.core.blocks.traits.{TColorBlock, TConnectedTextureBlock}
import com.projextxy.core.client.render.block.RenderSimpleGlow
import com.projextxy.core.reference.{MCColors, ModColors}
import com.projextxy.core.{ProjectXYCore, ProjectXYCoreProxy}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EnumCreatureType
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess

trait TBlockXyGlow extends BlockXy with TColorBlock {
  var colors: List[MCColors] = ModColors.xyColors

  override def damageDropped(meta: Int): Int = meta

  override def canCreatureSpawn(creatureType: EnumCreatureType, world: IBlockAccess, x: Int, y: Int, z: Int): Boolean = false

  def getBrightness(world: IBlockAccess, x: Int, y: Int, z: Int): Int = 220

  def getBrightness(meta: Int): Int = 220

  def getAnimationIcon: IIcon = TBlockXyGlow.animationIcon

  def setColors(colors: List[MCColors]): Block = {
    this.colors = colors
    this
  }

  override def getRenderType: Int = RenderSimpleGlow.renderId
}

object TBlockXyGlow {
  lazy val animationIcon: IIcon = ProjectXYCoreProxy.animationFx.texture
}

/**
 * The main implementation of glow blocks
 */
class BlockXyGlow(material: Material = Material.rock, colorMultiplier: Boolean = false) extends BlockXy(material) with TBlockXyGlow {
  override val hasColorMultiplier: Boolean = colorMultiplier

  override def registerBlockIcons(iconRegister: IIconRegister) = {
    blockIcon = iconRegister.registerIcon(s"${ProjectXYCore.MOD_ID}:overlay/$getTextureName")
  }

  override def getSubBlocks(item: Item, tabs: CreativeTabs, _list: util.List[_]) = {
    val list: util.List[ItemStack] = _list
    for (colorIndex <- colors.indices) {
      list.add(new ItemStack(this, 1, colorIndex))
    }
  }

  override def getColor(iBlockAccess: IBlockAccess, x: Int, y: Int, z: Int): Int = colors(iBlockAccess.getBlockMetadata(x, y, z)).rgb

  override def getColor(meta: Int): Int = colors(meta).rgb
}

object BlockXyGlowConnected {
  def apply(name: String, texture: String, mat: Material = Material.rock, colors: List[MCColors] = ModColors.xyColors, colorMultiplier: Boolean = false, renderBlockIcon: Boolean = false): BlockXyGlow = {
    val block = if (colorMultiplier) {
      new BlockXyGlow(mat) with TConnectedTextureBlock {
        val connectedFolder = texture
        val renderBlockTexture = renderBlockIcon
        override val hasColorMultiplier = true
      }
    } else {
      new BlockXyGlow(mat) with TConnectedTextureBlock {
        val connectedFolder = texture
        val renderBlockTexture = renderBlockIcon
        override val hasColorMultiplier = false
      }
    }
    block.setBlockName(name)
    block.setColors(colors)
    block
  }
}

object BlockXyGlow {
  def apply(material: Material = Material.rock, colorMultiplier: Boolean = false, name: String, colors: List[MCColors] = ModColors.xyColors): BlockXyGlow = new BlockXyGlow(material, colorMultiplier).setBlockName(name).setColors(colors)

  implicit def asBlockXyGlow(block: Block): BlockXyGlow = block.asInstanceOf[BlockXyGlow]
}


