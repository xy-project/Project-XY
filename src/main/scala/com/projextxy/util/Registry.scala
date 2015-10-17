package com.projextxy.util

import java.io.{File, PrintWriter}
import com.projextxy.core.blocks.glow.BlockXyGlow
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.item.ItemBlock

object Registry {
  val doLocalisationExportBecauseImLazy = false
  val file = if (doLocalisationExportBecauseImLazy) new PrintWriter(new File("lang.txt")) else null

  def registerBlock(block: Block): Block = registerBlock(block, classOf[ItemBlock])

  def registerBlock(block: Block, itemBlock: Class[_ <: ItemBlock]) = {
    if (doLocalisationExportBecauseImLazy) {
      block match {
        case blockXyGlow: BlockXyGlow =>
          blockXyGlow.colors.indices.foreach { x => file.write(blockXyGlow.getUnlocalizedName + "|" + x + ".name=" + blockXyGlow.colors(x).name + "\n") }
        case _ =>
          file.write(block.getUnlocalizedName + ".name=\n")
      }
    }
    if ("tile.null" == block.getUnlocalizedName) throw new RuntimeException(String.format("Block is miising a proper name: %s", block.getClass.getName))
    LogHelper.info("Registering Block: \"%s\" as \"%s\" with item \"%s\"", block.getClass.getSimpleName, block.getUnlocalizedName.substring("tile.".length), itemBlock.getSimpleName)
    GameRegistry.registerBlock(block, itemBlock, block.getUnlocalizedName.substring("tile.".length))
  }
}
