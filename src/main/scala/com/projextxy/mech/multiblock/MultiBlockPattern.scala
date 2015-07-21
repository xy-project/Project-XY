package com.projextxy.mech.multiblock

case class MultiBlockPattern(width: Int, height: Int, depth: Int) {
  val pattern = Array.ofDim[BlockMulti](width, height, depth)

  def fillWithBlock(minx: Int, miny: Int, minz: Int, maxx: Int, maxy: Int, maxz: Int, blockMulti: BlockMulti): Unit = {
    for (xIndex <- minx to maxx)
      for (yIndex <- miny to maxy)
        for (zIndex <- minz to maxz)
          pattern(xIndex)(yIndex)(zIndex) = blockMulti
  }
}
