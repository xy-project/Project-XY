package com.projextxy.core.blocks.glow

import java.util

import com.projextxy.core.CoreItems
import com.projextxy.core.client.render.block.RenderSimpleGlow
import com.projextxy.core.reference.ModColors
import net.minecraft.block.material.Material
import net.minecraft.item.ItemStack
import net.minecraft.util.MathHelper
import net.minecraft.world.World

/**
 * Created by Adam on 7/12/2015.
 */
class BlockXyOre extends BlockXyGlow(Material.rock, RenderSimpleGlow.modelId) {
  val min_drop = 4
  val max_drop = 6

  setBlockName("blockXyOre")
  setHardness(2.0F)
  setHarvestLevel("pickaxe", 1)

  colors = ModColors.xyColors

  override def getDrops(world: World, x: Int, y: Int, z: Int, metadata: Int, fortune: Int): util.ArrayList[ItemStack] = {
    val count: Int = min_drop + world.rand.nextInt(max_drop - min_drop)
    dropXpOnBlockBreak(world, x, y, z, MathHelper.getRandomIntegerInRange(world.rand, 2, 5))
    val list = new util.ArrayList[ItemStack]()
    list.add(new ItemStack(CoreItems.itemXychorium, count, metadata))
    list
  }
}
