package com.projextxy.mech.multiblock

import codechicken.lib.vec.BlockCoord
import com.projextxy.mech.MechBlocks
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.world.World

/**
 * Checks if a block matches based on category.
 * Ex any type of block that is ROCK
 */
object MultiCategoryMatcher {
  def getCategoryForBlock(block: Block): MultiShadowCategory = {
    if (block == null || block.getMaterial == Material.air) {
      return MultiShadowCategory.AIR
    } else if (block.getMaterial == Material.glass) {
      return MultiShadowCategory.GLASS
    } else if (block.renderAsNormalBlock() && block.getMaterial.isOpaque) {
      if (block.getMaterial == Material.rock || block.getMaterial == Material.iron) {
        return MultiShadowCategory.ROCK
      } else if (block.getMaterial == Material.wood) {
        return MultiShadowCategory.WOOD
      } else {
        return MultiShadowCategory.GRASS
      }
    }
    MultiShadowCategory.NONE
  }
}

case class MultiShadowCategoryMatcher(category: MultiShadowCategory) extends MultiBlockMatcher {
  override def matches(world: World, pos: BlockCoord): Boolean = MultiCategoryMatcher.getCategoryForBlock(world.getBlock(pos.x, pos.y, pos.z)) == category
}

object MultiShadowCategoryMatcher {
  val AIR = new MultiShadowCategoryMatcher(MultiShadowCategory.AIR)
  val ROCK = new MultiShadowCategoryMatcher(MultiShadowCategory.ROCK)
  val WOOD = new MultiShadowCategoryMatcher(MultiShadowCategory.WOOD)
  val GLASS = new MultiShadowCategoryMatcher(MultiShadowCategory.GLASS)
  val GRASS = new MultiShadowCategoryMatcher(MultiShadowCategory.GRASS)
  val ANY_OPAQUE_EXCEPT_VALVE = new MultiBlockMatcher {
    override def matches(world: World, pos: BlockCoord): Boolean = {
      if (world.getBlock(pos.x, pos.y, pos.z).isInstanceOf[BlockMultiTank])
        return false
      val category = MultiCategoryMatcher.getCategoryForBlock(world.getBlock(pos.x, pos.y, pos.z))
      category == MultiShadowCategory.ROCK || category == MultiShadowCategory.WOOD || category == MultiShadowCategory.GRASS
    }
  }
  val ANY_BUT_AIR = new MultiBlockMatcher {
    override def matches(world: World, pos: BlockCoord): Boolean = {
      val category = MultiCategoryMatcher.getCategoryForBlock(world.getBlock(pos.x, pos.y, pos.z))
      category == MultiShadowCategory.ROCK || category == MultiShadowCategory.WOOD || category == MultiShadowCategory.GRASS || category == MultiShadowCategory.GLASS
    }
  }
}

/**
 * Check if the block matches the specified pattern.
 */
abstract class MultiBlockMatcher {
  def matches(world: World, pos: BlockCoord): Boolean
}

