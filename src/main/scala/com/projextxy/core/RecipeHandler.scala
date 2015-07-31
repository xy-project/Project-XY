package com.projextxy.core

import java.lang.{Character => JChar}

import com.projextxy.core.blocks.glow.BlockXyGlow
import com.projextxy.core.item.TColoredItem
import com.projextxy.core.reference.{MCColors, ModColors}
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.ItemStack

object RecipeHandler {
  def init(): Unit = {
    for (color <- ModColors.xyColors) {
      addStorageRecipe(createXyColoredStack(CoreItems.itemXychoridite, color), createXyColoredStack(CoreBlocks.blockXyStorage, color))
      GameRegistry.addSmelting(createXyColoredStack(CoreItems.itemXychorium, color), createXyColoredStack(CoreItems.itemXychoridite, color), 4)
    }
  }

  private def addStorageRecipe(in: ItemStack, out: ItemStack) = GameRegistry.addRecipe(out, "xxx", "xxx", "xxx", 'x': JChar, in)

  private def createXyColoredStack(block: BlockXyGlow, mCColors: MCColors) = new ItemStack(block, 1, block.colors.indexOf(mCColors))

  private def createXyColoredStack(block: TColoredItem, mCColors: MCColors) = new ItemStack(block, 1, block.colors.indexOf(mCColors))
}
