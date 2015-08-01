package com.projextxy.mech

import com.projextxy.core.blocks.glow.BlockXyGlow
import com.projextxy.core.blocks.item.ItemBlockColor
import com.projextxy.mech.block.BlockXyBasicMachine
import com.projextxy.util.Registry._

object MechBlocks {

  var blockXyBasicMachine: BlockXyGlow = null

  def init(): Unit = {
    blockXyBasicMachine = new BlockXyBasicMachine

    registerBlock(blockXyBasicMachine, classOf[ItemBlockColor])
  }
}
