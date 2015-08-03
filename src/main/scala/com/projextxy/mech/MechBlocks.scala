package com.projextxy.mech

import com.projextxy.core.blocks.glow.BlockXyGlow
import com.projextxy.core.blocks.item.ItemBlockColor
import com.projextxy.mech.block.{BlockFabricator, BlockXyBasicMachine}
import com.projextxy.util.Registry._

object MechBlocks {

  var blockXyBasicMachine: BlockXyGlow = null
  var blockFabricator: BlockFabricator = null

  def init(): Unit = {
    blockXyBasicMachine = new BlockXyBasicMachine
    blockFabricator = new BlockFabricator

    registerBlock(blockXyBasicMachine, classOf[ItemBlockColor])
    registerBlock(blockFabricator)
  }
}
