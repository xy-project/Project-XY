package com.projextxy.mech.multiblock

import java.util
import java.util.Random

import com.projextxy.core.blocks.BlockXy
import com.projextxy.mech.client.RenderBlockMultiShadow
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.particle.EffectRenderer
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.{AxisAlignedBB, IIcon, MovingObjectPosition}
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.common.util.ForgeDirection

class BlockMultiShadow(material: Material, suffix: String) extends BlockXy(material) with BlockMulti {
  setBlockName("blockMultiShadow" + suffix)
  setCreativeTab(null)
  material match {
    case Material.wood => setStepSound(Block.soundTypeWood)
    case Material.glass => setStepSound(Block.soundTypeGlass)
    case Material.ground => setStepSound(Block.soundTypeGrass)
    case _ =>
  }

  override def createNewTileEntity(world: World, meta: Int): TileEntity = new TileMultiShadow

  override def getRenderType: Int = RenderBlockMultiShadow.renderId

  override def updateTick(world: World, x: Int, y: Int, z: Int, rand: Random): Unit = {
    super.updateTick(world, x, y, z, rand)
    val shadowBlock = getShadowBlock(world, x, y, z)
    if (shadowBlock.isDefined) {
      shadowBlock.get.block.updateTick(world, x, y, z, rand)
    }
  }


  override def getCollisionBoundingBoxFromPool(world: World, x: Int, y: Int, z: Int): AxisAlignedBB = {
    val shadowBlock = getShadowBlock(world, x, y, z)
    if (shadowBlock.isDefined) {
      return shadowBlock.get.block.getCollisionBoundingBoxFromPool(world, x, y, z)
    }
    super.getCollisionBoundingBoxFromPool(world, x, y, z)
  }

  override def isFireSource(world: World, x: Int, y: Int, z: Int, side: ForgeDirection): Boolean = {
    val shadowBlock = getShadowBlock(world, x, y, z)
    if (shadowBlock.isDefined) {
      shadowBlock.get.block.isFireSource(world, x, y, z, side)
    } else {
      false
    }
  }

  override def isAir(world: IBlockAccess, x: Int, y: Int, z: Int): Boolean = blockMaterial == Material.air

  override def isOpaqueCube: Boolean = blockMaterial != Material.glass && blockMaterial != Material.air

  override def renderAsNormalBlock(): Boolean = blockMaterial != Material.glass && blockMaterial != Material.air

  override def getIcon(world: IBlockAccess, x: Int, y: Int, z: Int, side: Int): IIcon = {
    val shadowBlock = getShadowBlock(world, x, y, z)
    if (shadowBlock.isDefined) {
      shadowBlock.get.block.getIcon(world, x, y, z, side)
    } else {
      null
    }
  }


  override def canCollideCheck(par1: Int, par2: Boolean): Boolean = blockMaterial != Material.air

  override def shouldSideBeRendered(world: IBlockAccess, x: Int, y: Int, z: Int, side: Int): Boolean = {
    if (blockMaterial == Material.glass) {
      val shadowBlock = getShadowBlock(world, x, y, z)
      if (shadowBlock.isDefined) {
        shadowBlock.get.block.shouldSideBeRendered(world, x, y, z, side)
      } else {
        super.shouldSideBeRendered(world, x, y, z, side)
      }
    }
    blockMaterial != Material.air
  }

  override def addDestroyEffects(world: World, x: Int, y: Int, z: Int, meta: Int, effectRenderer: EffectRenderer): Boolean = {
    val shadowBlock = getShadowBlock(world, x, y, z)
    if (shadowBlock.isDefined) {
      shadowBlock.get.block.addDestroyEffects(world, x, y, z, meta, effectRenderer)
    } else {
      super.addDestroyEffects(world, x, y, z, meta, effectRenderer)
    }
  }

  def getShadowBlock(world: IBlockAccess, x: Int, y: Int, z: Int): Option[BlockDef] = {
    world.getTileEntity(x, y, z) match {
      case tile: TileMultiShadow => tile.getCurrBlockDef
      case _ => None
    }
  }

  override def addHitEffects(world: World, target: MovingObjectPosition, effectRenderer: EffectRenderer): Boolean = {
    val shadowBlock = getShadowBlock(world, target.blockX, target.blockY, target.blockZ)
    if (shadowBlock.isDefined) {
      shadowBlock.get.block.addHitEffects(world, target, effectRenderer)
    } else {
      super.addHitEffects(world, target, effectRenderer)
    }
  }

  override def getPickBlock(target: MovingObjectPosition, world: World, x: Int, y: Int, z: Int, player: EntityPlayer): ItemStack = {
    val shadowBlock = getShadowBlock(world, target.blockX, target.blockY, target.blockZ)
    if (shadowBlock.isDefined) {
      shadowBlock.get.block.getPickBlock(target, world, x, y, z, player)
    } else {
      super.getPickBlock(target, world, x, y, z, player)
    }
  }

  override def getDrops(world: World, x: Int, y: Int, z: Int, metadata: Int, fortune: Int): util.ArrayList[ItemStack] = {
    val shadowBlock = getShadowBlock(world, x, y, z)
    if (shadowBlock.isDefined) {
      shadowBlock.get.block.getDrops(world, x, y, z, metadata, fortune)
    } else {
      super.getDrops(world, x, y, z, metadata, fortune)
    }
  }

  override def getBlockHardness(world: World, x: Int, y: Int, z: Int): Float = {
    val shadowBlock = getShadowBlock(world, x, y, z)
    if (shadowBlock.isDefined) {
      shadowBlock.get.block.getBlockHardness(world, x, y, z)
    } else {
      super.getBlockHardness(world, x, y, z)
    }
  }

  override def getPlayerRelativeBlockHardness(player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Float = {
    val shadowBlock = getShadowBlock(world, x, y, z)
    if (shadowBlock.isDefined) {
      shadowBlock.get.block.getPlayerRelativeBlockHardness(player, world, x, y, z)
    } else {
      super.getPlayerRelativeBlockHardness(player, world, x, y, z)
    }
  }

  override def getExplosionResistance(par1Entity: Entity, world: World, x: Int, y: Int, z: Int, explosionX: Double, explosionY: Double, explosionZ: Double): Float = {
    val shadowBlock = getShadowBlock(world, x, y, z)
    if (shadowBlock.isDefined) {
      shadowBlock.get.block.getExplosionResistance(par1Entity, world, x, y, z, explosionX, explosionY, explosionZ)
    } else {
      super.getExplosionResistance(par1Entity, world, x, y, z, explosionX, explosionY, explosionZ)
    }
  }

  override def onNeighborBlockChange(world: World, x: Int, y: Int, z: Int, block: Block): Unit = {
    val shadowBlock = getShadowBlock(world, x, y, z)
    if (shadowBlock.isDefined) {
      shadowBlock.get.block.onNeighborBlockChange(world, x, y, z, block)
    } else {
      super.onNeighborBlockChange(world, x, y, z, block)
    }
  }

  override def hasTileEntity(metadata: Int): Boolean = true

  override def removedByPlayer(world: World, player: EntityPlayer, x: Int, y: Int, z: Int, willHarvest: Boolean): Boolean = if (willHarvest) true else super.removedByPlayer(world, player, x, y, z, willHarvest)

  override def harvestBlock(world: World, player: EntityPlayer, x: Int, y: Int, z: Int, meta: Int) = {
    super.harvestBlock(world, player, x, y, z, meta)
    world.setBlockToAir(x, y, z)
  }
}
