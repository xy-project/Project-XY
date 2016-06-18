package com.projextxy.mech.multiblock

import codechicken.core.featurehack.{EntityRenderHook, EntityUpdateHook}
import codechicken.lib.data.{MCDataInput, MCDataOutput}
import codechicken.lib.packet.PacketCustom
import codechicken.lib.vec.BlockCoord
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraft.network.Packet
import net.minecraft.world.{ChunkCoordIntPair, World}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

abstract class MultiBlock(worldExt: XYWE, chunkExt: XYCE) extends EntityUpdateHook.IUpdateCallback with EntityRenderHook.IRenderCallback {
  val world = worldExt.world
  var valid = false
  val id = worldExt.nextAvailableMultiBlockId
  var requestsedUpdatePacket = false
  val inBlocks = new ArrayBuffer[BlockCoord]()
  val inChunks = new mutable.HashSet[ChunkCoordIntPair]()

  def this(worldObj: World, location: ChunkCoordIntPair) =
    this(XYWEI.getExtension(worldObj).asInstanceOf[XYWE], XYWEI.getExtension(worldObj).getChunkExtension(location.chunkXPos, location.chunkZPos).asInstanceOf[XYCE])


  /**
   * @param blockPos the position of the block being clicked
   * @param player the player
   * @return a int value corresponding to the type of action that should be completed after the block has been activated, 0 - nothing, 1 - finish, 2 - continue or finish at end
   */
  def onActivated(blockPos: BlockCoord, player: EntityPlayer): Int = 0

  /**
   * Called before adding to the world
   */
  def initiate(): Boolean = {
    for (pos <- inBlocks) {
      world.getTileEntity(pos.x, pos.y, pos.z) match {
        case noop: TileMultiBlock =>
        case _ =>
          if (!world.isRemote) {
            val tile = MultiBlockManager.convertBlockToShadow(world, pos.x, pos.y, pos.z)
            if (tile.isEmpty)
              return false
          } else {
            //Client side should always be false if the tile does not conform to TileMultiBlock. The server can fix itself.
            return false
          }
      }
      onJoinTile(pos)
    }

    for (pos <- inBlocks) {
      world.getTileEntity(pos.x, pos.y, pos.z) match {
        case multiblock: TileMultiBlock => multiblock.joinMultiBlock(this)
        case _ => sys.error("Tile is invalid.")
      }
    }
    //Entity should always spawn at the head of the positions
    val position = inBlocks.head
    valid = true
    world.spawnEntityInWorld(new EntityUpdateHook(world, position.x, position.y, position.z, this))
    if (world.isRemote)
      world.spawnEntityInWorld(new EntityRenderHook(world, position.x, position.y, position.z, this))
    true
  }

  /**
   * unloads from the world
   * @param remove weather it's being removing from the world or not.
   */
  def unload(remove: Boolean): Unit = {
    valid = false
    for (coord <- inBlocks) {
      world.getTileEntity(coord.x, coord.y, coord.z) match {
        case tile: TileMultiBlock => tile.removeMultiBlock(this)
        case _ =>
      }
    }
  }

  /**
   * @return true if all chunks are loaded. Used to detect if the multiblock should be loaded into the world
   */
  def chunksLoaded(): Boolean = {
    for (chunk <- inChunks)
      if (worldExt.getChunkExtension(chunk) == null)
        return false
    true
  }

  /**
   * Adds a block to the structure
   * @param blockPos the position of the block
   */
  def addTile(blockPos: BlockCoord): Unit = {
    inBlocks += blockPos
    inChunks += new ChunkCoordIntPair(blockPos.x >> 4, blockPos.z >> 4)
  }

  /**
   * An event called during initialization
   * @param blockPos the position of the the that the multiblock joined.
   */
  def onJoinTile(blockPos: BlockCoord): Unit = {}

  /**
   * Looped when the multiblock is valid
   */
  override def onUpdate(): Unit = {
  }

  /**
   * From the entity update hook.
   * @return
   */
  override def isValid: Boolean = valid

  /**
   * @return the packet that sends to the client when either added or reloaded or another player is added to the world
   */
  def getDescriptionPacket(): Packet = {
    val packet = new PacketCustom(MultiBlocksCPH.CHANNEL, 1)
    packet.writeInt(id)
    packet.writeInt(getMultiBlockId.ordinal())
    packet.writeInt(chunkExt.coord.chunkXPos)
    packet.writeInt(chunkExt.coord.chunkZPos)
    packet.writeNBTTagCompound(writeBlockCoords(new NBTTagCompound))
    writeToDescriptionPacket(packet)
    packet.toPacket
  }

  def handleDescriptionPacket(packet: PacketCustom): Unit = {
    readBlockCoords(packet.readNBTTagCompound())
    readFromDescriptionPacket(packet)
  }

  /**
   * Writes the block coords that compromises the structure to NBT so it can be sent to client and be saved to NBT
   */
  def writeBlockCoords(out: NBTTagCompound): NBTTagCompound = {
    val list = new NBTTagList
    for (blockCoord <- inBlocks) {
      val inner = new NBTTagCompound
      inner.setInteger("x", blockCoord.x)
      inner.setInteger("y", blockCoord.y)
      inner.setInteger("z", blockCoord.z)
      list.appendTag(inner)
    }
    out.setTag("coords", list)
    out
  }

  /**
   * Reads the block coords that are written to NBT
   */
  def readBlockCoords(in: NBTTagCompound): Unit = {
    inBlocks.clear()
    val list = in.getTagList("coords", 10)
    for (i <- 0 until list.tagCount()) {
      val innerList = list.getCompoundTagAt(i)
      addTile(new BlockCoord(innerList.getInteger("x"), innerList.getInteger("y"), innerList.getInteger("z")))
    }
  }

  def writeToNBT(nbt: NBTTagCompound): Unit = {
    writeBlockCoords(nbt)
  }

  def readFromNBT(nbt: NBTTagCompound): Unit = {
    readBlockCoords(nbt)
  }

  /**
   * The type id so that the server and client can recreate the block when reloaded.
   * @return
   */
  def getMultiBlockId: MultiBlockId

  def writeToDescriptionPacket(out: MCDataOutput): Unit = {}

  def readFromDescriptionPacket(in: MCDataInput): Unit = {}

  def writeToUpdatePacket(out: MCDataOutput): Unit = {}

  def readFromUpdatePacket(in: MCDataInput): Unit = {}

  def getUpdatePacket: Packet = {
    val packet = new PacketCustom(MultiBlocksCPH.CHANNEL, 3)
    packet.writeInt(id)
    writeToUpdatePacket(packet)
    packet.toPacket
  }

  def requestUpdate(): Unit = chunkExt.markMultiBlockForUpdate(this)

  def getChunkExt: XYCE = chunkExt

  def getWorldExt: XYWE = worldExt

  @SideOnly(Side.CLIENT)
  override def shouldRenderInPass(i: Int): Boolean = i == 0

  @SideOnly(Side.CLIENT)
  override def render(v: Float, i: Int): Unit = {}

  /**
   * Idk why but this fixes a bug
   */
  override def equals(obj: scala.Any): Boolean = super.equals(obj)
}
