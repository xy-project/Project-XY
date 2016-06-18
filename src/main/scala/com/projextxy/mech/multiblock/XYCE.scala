package com.projextxy.mech.multiblock

import codechicken.lib.world.ChunkExtension
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraft.network.Packet
import net.minecraft.world.chunk.Chunk
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class XYCE(chunk2: Chunk, worldExt: XYWE) extends ChunkExtension(chunk2, worldExt) {
  private val multiBlocks = new mutable.HashSet[MultiBlock]()
  private val updateMultiBlocks = new mutable.HashSet[MultiBlock]()
  private val packetQueue = new ArrayBuffer[Packet]()
  var requestedUpdatePackets = false


  def addMultiBlock(multiBlock: MultiBlock): Unit = {
    multiBlocks += multiBlock
    chunk.setChunkModified()
  }

  def removeMultiBlock(multiBlock: MultiBlock): Unit = multiBlocks.remove(multiBlock)

  override def sendUpdatePackets(): Unit = {
    requestedUpdatePackets = false
    for (packet <- packetQueue)
      sendPacketToPlayers(packet)
    for (multiBlock <- updateMultiBlocks) {
      sendPacketToPlayers(multiBlock.getUpdatePacket)
      updateMultiBlocks -= multiBlock
      multiBlock.requestsedUpdatePacket = false
    }
    packetQueue.clear()
  }

  def sendMultiBlockPacket(packet: Packet): Unit = {
    packetQueue += packet
    requestUpdatePacket
  }

  def requestUpdatePacket = {
    worldExt.chunkPackets += this
    requestedUpdatePackets = true
  }

  override def saveData(tag: NBTTagCompound): Unit = {
    val mulitBlockNBT = new NBTTagList
    for (multiBlock <- multiBlocks) {
      if (multiBlock.getChunkExt == this) {
        val nbt = new NBTTagCompound
        nbt.setInteger("multiBlockId", multiBlock.id)
        nbt.setInteger("multiBlockType", multiBlock.getMultiBlockId.ordinal())
        multiBlock.writeToNBT(nbt)
        mulitBlockNBT.appendTag(nbt)
      }
    }
    //Set the tag to something unique so it doesn't collide with other mods.
    tag.setTag("ProjectXyMulti", mulitBlockNBT)
  }

  override def loadData(tag: NBTTagCompound): Unit = {
    val tagList = tag.getTagList("ProjectXyMulti", 10)
    for (i <- 0 until tagList.tagCount()) {
      val nbt = tagList.getCompoundTagAt(i)
      val multiblock = MultiBlockManager.createMultiBlock(MultiBlockId.values()(nbt.getInteger("multiBlockType")), worldExt, this)
      multiblock.readFromNBT(nbt)
      worldExt.unloadMutliBlock(multiblock)
    }
  }

  override def unload(): Unit = {
    for (multiblock <- multiBlocks) {
      if (multiblock.getChunkExt == this || world.world.isRemote) {
        worldExt.removeMultiBlock(multiblock, remove = false)
      } else {
        worldExt.unloadMutliBlock(multiblock)
      }
    }
  }

  /**
   * Send update packet to player when chunk is loaded.
   * @param player
   */
  override def onWatchPlayer(player: EntityPlayerMP): Unit = {
    for (multiBlock <- multiBlocks) {
      if (multiBlock.getChunkExt == this)
        player.playerNetServerHandler.sendPacket(multiBlock.getDescriptionPacket())
    }
  }

  def markMultiBlockForUpdate(multiBlock: MultiBlock): Unit = {
    if (multiBlock.requestsedUpdatePacket)
      return
    chunk.setChunkModified()
    requestUpdatePacket
    updateMultiBlocks += multiBlock
    multiBlock.requestsedUpdatePacket = true
  }
}
