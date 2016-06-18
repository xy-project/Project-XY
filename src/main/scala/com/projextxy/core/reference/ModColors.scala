package com.projextxy.core.reference

import codechicken.lib.colour.ColourRGBA

object ModColors {
  lazy val xyColorsMap = Map("RED" -> MCColors.RED, "GREEN" -> MCColors.GREEN, "BLUE" -> MCColors.BLUE, "WHITE" -> MCColors.WHITE, "BLACK" -> MCColors.BLACK)
  lazy val xyColors = List(MCColors.RED, MCColors.GREEN, MCColors.BLUE, MCColors.WHITE, MCColors.BLACK)
  lazy val mcColors = MCColors.VALID_COLORS.toList

  lazy val basicMachineColors = List(MCColors.BLUE, MCColors.BLACK, MCColors.WHITE, MCColors.GREEN)
  lazy val machineColor = new ColourRGBA(0, 100, 255, 255)
}
