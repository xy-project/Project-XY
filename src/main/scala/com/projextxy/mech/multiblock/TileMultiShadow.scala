package com.projextxy.mech.multiblock

import codechicken.lib.data.{MCDataInput, MCDataOutput}
import com.projextxy.core.tile.traits.TMachineTile
import net.minecraft.block.Block
import net.minecraft.nbt.NBTTagCompound

class TileMultiShadow extends TileMultiBlock with TMachineTile {
  private var currBlock: BlockDef = null

  def setCurrentBlock(block: Block, meta: Int): Unit = setCurrentBlock(new BlockDef(block, meta))

  def setCurrentBlock(blockDef: BlockDef) = currBlock = blockDef

  def getCurrMeta: Option[Int] = if (currBlock != null) Some(currBlock.meta) else None

  def getCurrBLock: Option[Block] = if (currBlock != null) Some(currBlock.block) else None

  def getCurrBlockDef: Option[BlockDef] = Option(currBlock)

  override def saveNBT(compound: NBTTagCompound): Unit = {
    if (currBlock != null)
      currBlock.write(compound)
  }

  override def receivePacket(in: MCDataInput): Unit = {
    val block = BlockDef.read(in)
    if (block.isDefined) {
      setCurrentBlock(block.get)
    }
  }

  override def writeToPacket(out: MCDataOutput): Unit = {
    if (currBlock != null) {
      currBlock.write(out)
    } else {
      out.writeInt(-1)
      out.writeInt(-1)
    }
  }

  override def readNBT(compound: NBTTagCompound): Unit = {
    BlockDef.read(compound).foreach(value => setCurrentBlock(value))
  }
}

case class BlockDef(block: Block, meta: Int) {
  def write(nbt: NBTTagCompound): Unit = {
    if (block != null) {
      nbt.setInteger("blockId", Block.getIdFromBlock(block))
      nbt.setInteger("metaData", meta)
    }
  }

  def write(out: MCDataOutput): Unit = {
    if (block != null) {
      out.writeInt(Block.getIdFromBlock(block))
      out.writeInt(meta)
    }
  }
}

object BlockDef {
  def read(nbt: NBTTagCompound): Option[BlockDef] = {
    if (nbt.hasKey("blockId")) {
      val block = Block.getBlockById(nbt.getInteger("blockId"))
      return Some(new BlockDef(block, nbt.getInteger("metData")))
    }
    None
  }

  def read(in: MCDataInput): Option[BlockDef] = {
    val block = in.readInt()
    val meta = in.readInt()
    Some(new BlockDef(Block.getBlockById(block), meta))
  }
}
