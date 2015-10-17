package com.projextxy.mech.multiblock

import codechicken.lib.world.{ChunkExtension, WorldExtension, WorldExtensionInstantiator}
import net.minecraft.world.World
import net.minecraft.world.chunk.Chunk

//XYWorldExtensionInstantiator renamed to be shorter
object XYWEI extends WorldExtensionInstantiator {
  override def createWorldExtension(world: World): WorldExtension = new XYWE(world)

  override def createChunkExtension(chunk: Chunk, worldExtension: WorldExtension): ChunkExtension = new XYCE(chunk, worldExtension.asInstanceOf[XYWE])

  def getExtensionXy(world: World): XYWE = getExtension(world).asInstanceOf[XYWE]
}
