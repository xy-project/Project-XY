package com.projextxy.core

import java.lang.{Character => JChar}

import com.projextxy.core.blocks.glow.BlockXyGlow
import com.projextxy.core.item.TColoredItem
import com.projextxy.core.reference.{MCColors, ModColors}
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.ShapedOreRecipe

object RecipeHandler {
  def init(): Unit = {
    for (color <- ModColors.xyColors) {
      addStorageRecipe(createXyColoredStack(CoreItems.itemXychoridite, color), createXyColoredStack(CoreBlocks.blockXyStorage, color))
      GameRegistry.addSmelting(createXyColoredStack(CoreItems.itemXychorium, color), createXyColoredStack(CoreItems.itemXychoridite, color), 4)
      GameRegistry.addRecipe(
        createXyColoredStack(CoreBlocks.blockXyBrick, color), "xyx", "yyy", "xyx",
        'x': JChar, createXyColoredStack(CoreItems.itemXychoridite, color),
        'y': JChar, Blocks.stonebrick)
    }
  }

  private def addStorageRecipe(in: ItemStack, out: ItemStack) = {
    GameRegistry.addRecipe(out, "xxx", "xxx", "xxx", 'x': JChar, in)
    in.stackSize = 9
    GameRegistry.addShapelessRecipe(in, out)
  }

  private def createXyColoredStack(block: BlockXyGlow, mCColors: MCColors) = new ItemStack(block, 1, block.colors.indexOf(mCColors))

  private def createXyColoredStack(block: TColoredItem, mCColors: MCColors) = new ItemStack(block, 1, block.colors.indexOf(mCColors))
}
