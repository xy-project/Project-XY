package com.projextxy.util

object MathHelper {
  def distanceBetweenPointsSpace(x1: Double, y1: Double, z1: Double, x2: Double, y2: Double, z2: Double): Float = math.sqrt(math.pow(x1 - x2, 2) + math.pow(y1 - y2, 2) + math.pow(z1 - z2, 2)).toFloat
}
