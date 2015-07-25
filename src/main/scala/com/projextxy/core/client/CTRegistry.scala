package com.projextxy.core.client

import codechicken.lib.render.TextureUtils
import codechicken.lib.render.TextureUtils.IIconSelfRegister
import com.projextxy.core.ProjectXYCore
import net.minecraft.block.Block
import net.minecraft.client.renderer.texture.{IIconRegister, TextureUtil}
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import org.apache.commons.compress.archivers.sevenz.Folder

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object CTRegistry extends IIconSelfRegister {
  val CLEAN_CONNECTED_FOLDER = "cleanConnected"

  val ctms = mutable.HashMap[String, Array[IIcon]]()
  val folders = new ArrayBuffer[String]()

  def init(): Unit = {
    addConnectedTexture(CLEAN_CONNECTED_FOLDER)

    TextureUtils.addIconRegistrar(this)
  }

  private def addConnectedTexture(folder: String): Unit = folders += folder

  override def atlasIndex(): Int = 0

  override def registerIcons(reg: IIconRegister): Unit = {
    for (folder <- folders) {
      val icons = Array.fill[IIcon](16)(null)
      icons(0) = reg.registerIcon(ProjectXYCore.MOD_ID.toLowerCase + ":overlay/" + folder + "/mainFace")
      icons(1) = reg.registerIcon(ProjectXYCore.MOD_ID.toLowerCase + ":overlay/" + folder + "/face_1_d")
      icons(2) = reg.registerIcon(ProjectXYCore.MOD_ID.toLowerCase + ":overlay/" + folder + "/face_1_u")
      icons(3) = reg.registerIcon(ProjectXYCore.MOD_ID.toLowerCase + ":overlay/" + folder + "/face_1_l")
      icons(4) = reg.registerIcon(ProjectXYCore.MOD_ID.toLowerCase + ":overlay/" + folder + "/face_1_r")
      icons(5) = reg.registerIcon(ProjectXYCore.MOD_ID.toLowerCase + ":overlay/" + folder + "/face_2_h")
      icons(6) = reg.registerIcon(ProjectXYCore.MOD_ID.toLowerCase + ":overlay/" + folder + "/face_2_v")
      icons(7) = reg.registerIcon(ProjectXYCore.MOD_ID.toLowerCase + ":overlay/" + folder + "/face_2_dl")
      icons(8) = reg.registerIcon(ProjectXYCore.MOD_ID.toLowerCase + ":overlay/" + folder + "/face_2_dr")
      icons(9) = reg.registerIcon(ProjectXYCore.MOD_ID.toLowerCase + ":overlay/" + folder + "/face_2_ul")
      icons(10) = reg.registerIcon(ProjectXYCore.MOD_ID.toLowerCase + ":overlay/" + folder + "/face_2_ur")
      icons(11) = reg.registerIcon(ProjectXYCore.MOD_ID.toLowerCase + ":overlay/" + folder + "/face_3_d")
      icons(12) = reg.registerIcon(ProjectXYCore.MOD_ID.toLowerCase + ":overlay/" + folder + "/face_3_u")
      icons(13) = reg.registerIcon(ProjectXYCore.MOD_ID.toLowerCase + ":overlay/" + folder + "/face_3_l")
      icons(14) = reg.registerIcon(ProjectXYCore.MOD_ID.toLowerCase + ":overlay/" + folder + "/face_3_r")
      icons(15) = reg.registerIcon(ProjectXYCore.MOD_ID.toLowerCase + ":overlay/" + folder + "/face_4")
      ctms.put(folder, icons)
    }
  }

  def getConnectedBlockTexture(folder: String, world: IBlockAccess, x: Int, y: Int, z: Int, side: Int, connectBlock: Block): IIcon = {
    var isOpenUp: Boolean = false
    var isOpenDown: Boolean = false
    var isOpenLeft: Boolean = false
    var isOpenRight: Boolean = false
    val optionalIcons = ctms.get(folder)
    if (optionalIcons.isEmpty)
      return null
    val icons = optionalIcons.get
    side match {
      case 0 =>
        if (shouldConnectToBlock(world, x, y, z, world.getBlock(x - 1, y, z), world.getBlockMetadata(x - 1, y, z), connectBlock)) isOpenDown = true
        if (shouldConnectToBlock(world, x, y, z, world.getBlock(x + 1, y, z), world.getBlockMetadata(x + 1, y, z), connectBlock)) isOpenUp = true
        if (shouldConnectToBlock(world, x, y, z, world.getBlock(x, y, z - 1), world.getBlockMetadata(x, y, z - 1), connectBlock)) isOpenLeft = true
        if (shouldConnectToBlock(world, x, y, z, world.getBlock(x, y, z + 1), world.getBlockMetadata(x, y, z + 1), connectBlock)) isOpenRight = true
        if (isOpenUp && isOpenDown && isOpenLeft && isOpenRight) return icons(15)
        else if (isOpenUp && isOpenDown && isOpenLeft) return icons(11)
        else if (isOpenUp && isOpenDown && isOpenRight) return icons(12)
        else if (isOpenUp && isOpenLeft && isOpenRight) return icons(13)
        else if (isOpenDown && isOpenLeft && isOpenRight) return icons(14)
        else if (isOpenDown && isOpenUp) return icons(5)
        else if (isOpenLeft && isOpenRight) return icons(6)
        else if (isOpenDown && isOpenLeft) return icons(8)
        else if (isOpenDown && isOpenRight) return icons(10)
        else if (isOpenUp && isOpenLeft) return icons(7)
        else if (isOpenUp && isOpenRight) return icons(9)
        else if (isOpenDown) return icons(3)
        else if (isOpenUp) return icons(4)
        else if (isOpenLeft) return icons(2)
        else if (isOpenRight) return icons(1)
      case 1 =>
        if (shouldConnectToBlock(world, x, y, z, world.getBlock(x - 1, y, z), world.getBlockMetadata(x - 1, y, z), connectBlock)) isOpenDown = true
        if (shouldConnectToBlock(world, x, y, z, world.getBlock(x + 1, y, z), world.getBlockMetadata(x + 1, y, z), connectBlock)) isOpenUp = true
        if (shouldConnectToBlock(world, x, y, z, world.getBlock(x, y, z - 1), world.getBlockMetadata(x, y, z - 1), connectBlock)) isOpenLeft = true
        if (shouldConnectToBlock(world, x, y, z, world.getBlock(x, y, z + 1), world.getBlockMetadata(x, y, z + 1), connectBlock)) isOpenRight = true
        if (isOpenUp && isOpenDown && isOpenLeft && isOpenRight) return icons(15)
        else if (isOpenUp && isOpenDown && isOpenLeft) return icons(11)
        else if (isOpenUp && isOpenDown && isOpenRight) return icons(12)
        else if (isOpenUp && isOpenLeft && isOpenRight) return icons(13)
        else if (isOpenDown && isOpenLeft && isOpenRight) return icons(14)
        else if (isOpenDown && isOpenUp) return icons(5)
        else if (isOpenLeft && isOpenRight) return icons(6)
        else if (isOpenDown && isOpenLeft) return icons(8)
        else if (isOpenDown && isOpenRight) return icons(10)
        else if (isOpenUp && isOpenLeft) return icons(7)
        else if (isOpenUp && isOpenRight) return icons(9)
        else if (isOpenDown) return icons(3)
        else if (isOpenUp) return icons(4)
        else if (isOpenLeft) return icons(2)
        else if (isOpenRight) return icons(1)
      case 2 =>
        if (shouldConnectToBlock(world, x, y, z, world.getBlock(x, y - 1, z), world.getBlockMetadata(x, y - 1, z), connectBlock)) {
          isOpenDown = true
        }
        if (shouldConnectToBlock(world, x, y, z, world.getBlock(x, y + 1, z), world.getBlockMetadata(x, y + 1, z), connectBlock)) {
          isOpenUp = true
        }
        if (shouldConnectToBlock(world, x, y, z, world.getBlock(x - 1, y, z), world.getBlockMetadata(x - 1, y, z), connectBlock)) {
          isOpenLeft = true
        }
        if (shouldConnectToBlock(world, x, y, z, world.getBlock(x + 1, y, z), world.getBlockMetadata(x + 1, y, z), connectBlock)) {
          isOpenRight = true
        }
        if (isOpenUp && isOpenDown && isOpenLeft && isOpenRight) return icons(15)
        else if (isOpenUp && isOpenDown && isOpenLeft) return icons(13)
        else if (isOpenUp && isOpenDown && isOpenRight) return icons(14)
        else if (isOpenUp && isOpenLeft && isOpenRight) return icons(11)
        else if (isOpenDown && isOpenLeft && isOpenRight) return icons(12)
        else if (isOpenDown && isOpenUp) return icons(6)
        else if (isOpenLeft && isOpenRight) return icons(5)
        else if (isOpenDown && isOpenLeft) return icons(9)
        else if (isOpenDown && isOpenRight) return icons(10)
        else if (isOpenUp && isOpenLeft) return icons(7)
        else if (isOpenUp && isOpenRight) return icons(8)
        else if (isOpenDown) return icons(1)
        else if (isOpenUp) return icons(2)
        else if (isOpenLeft) return icons(4)
        else if (isOpenRight) return icons(3)
      case 3 =>
        if (shouldConnectToBlock(world, x, y, z, world.getBlock(x, y - 1, z), world.getBlockMetadata(x, y - 1, z), connectBlock)) isOpenDown = true
        if (shouldConnectToBlock(world, x, y, z, world.getBlock(x, y + 1, z), world.getBlockMetadata(x, y + 1, z), connectBlock)) isOpenUp = true
        if (shouldConnectToBlock(world, x, y, z, world.getBlock(x - 1, y, z), world.getBlockMetadata(x - 1, y, z), connectBlock)) isOpenLeft = true
        if (shouldConnectToBlock(world, x, y, z, world.getBlock(x + 1, y, z), world.getBlockMetadata(x + 1, y, z), connectBlock)) isOpenRight = true
        if (isOpenUp && isOpenDown && isOpenLeft && isOpenRight) return icons(15)
        else if (isOpenUp && isOpenDown && isOpenLeft) return icons(14)
        else if (isOpenUp && isOpenDown && isOpenRight) return icons(13)
        else if (isOpenUp && isOpenLeft && isOpenRight) return icons(11)
        else if (isOpenDown && isOpenLeft && isOpenRight) return icons(12)
        else if (isOpenDown && isOpenUp) return icons(6)
        else if (isOpenLeft && isOpenRight) return icons(5)
        else if (isOpenDown && isOpenLeft) return icons(10)
        else if (isOpenDown && isOpenRight) return icons(9)
        else if (isOpenUp && isOpenLeft) return icons(8)
        else if (isOpenUp && isOpenRight) return icons(7)
        else if (isOpenDown) return icons(1)
        else if (isOpenUp) return icons(2)
        else if (isOpenLeft) return icons(3)
        else if (isOpenRight) return icons(4)
      case 4 =>
        if (shouldConnectToBlock(world, x, y, z, world.getBlock(x, y - 1, z), world.getBlockMetadata(x, y - 1, z), connectBlock)) isOpenDown = true
        if (shouldConnectToBlock(world, x, y, z, world.getBlock(x, y + 1, z), world.getBlockMetadata(x, y + 1, z), connectBlock)) isOpenUp = true
        if (shouldConnectToBlock(world, x, y, z, world.getBlock(x, y, z - 1), world.getBlockMetadata(x, y, z - 1), connectBlock)) isOpenLeft = true
        if (shouldConnectToBlock(world, x, y, z, world.getBlock(x, y, z + 1), world.getBlockMetadata(x, y, z + 1), connectBlock)) isOpenRight = true
        if (isOpenUp && isOpenDown && isOpenLeft && isOpenRight) return icons(15)
        else if (isOpenUp && isOpenDown && isOpenLeft) return icons(14)
        else if (isOpenUp && isOpenDown && isOpenRight) return icons(13)
        else if (isOpenUp && isOpenLeft && isOpenRight) return icons(11)
        else if (isOpenDown && isOpenLeft && isOpenRight) return icons(12)
        else if (isOpenDown && isOpenUp) return icons(6)
        else if (isOpenLeft && isOpenRight) return icons(5)
        else if (isOpenDown && isOpenLeft) return icons(10)
        else if (isOpenDown && isOpenRight) return icons(9)
        else if (isOpenUp && isOpenLeft) return icons(8)
        else if (isOpenUp && isOpenRight) return icons(7)
        else if (isOpenDown) return icons(1)
        else if (isOpenUp) return icons(2)
        else if (isOpenLeft) return icons(3)
        else if (isOpenRight) return icons(4)
      case 5 =>
        if (shouldConnectToBlock(world, x, y, z, world.getBlock(x, y - 1, z), world.getBlockMetadata(x, y - 1, z), connectBlock)) isOpenDown = true
        if (shouldConnectToBlock(world, x, y, z, world.getBlock(x, y + 1, z), world.getBlockMetadata(x, y + 1, z), connectBlock)) isOpenUp = true
        if (shouldConnectToBlock(world, x, y, z, world.getBlock(x, y, z - 1), world.getBlockMetadata(x, y, z - 1), connectBlock)) isOpenLeft = true
        if (shouldConnectToBlock(world, x, y, z, world.getBlock(x, y, z + 1), world.getBlockMetadata(x, y, z + 1), connectBlock)) isOpenRight = true
        if (isOpenUp && isOpenDown && isOpenLeft && isOpenRight) return icons(15)
        else if (isOpenUp && isOpenDown && isOpenLeft) return icons(13)
        else if (isOpenUp && isOpenDown && isOpenRight) return icons(14)
        else if (isOpenUp && isOpenLeft && isOpenRight) return icons(11)
        else if (isOpenDown && isOpenLeft && isOpenRight) return icons(12)
        else if (isOpenDown && isOpenUp) return icons(6)
        else if (isOpenLeft && isOpenRight) return icons(5)
        else if (isOpenDown && isOpenLeft) return icons(9)
        else if (isOpenDown && isOpenRight) return icons(10)
        else if (isOpenUp && isOpenLeft) return icons(7)
        else if (isOpenUp && isOpenRight) return icons(8)
        else if (isOpenDown) return icons(1)
        else if (isOpenUp) return icons(2)
        else if (isOpenLeft) return icons(4)
        else if (isOpenRight) return icons(3)
    }
    icons(0)
  }

  def getMainFaceIcon(folder: String): IIcon = {
    val optionalIcons = ctms.get(folder)
    if (optionalIcons.isEmpty)
      return null

    optionalIcons.get(0)
  }

  def shouldConnectToBlock(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, meta: Int, connectBlock: Block): Boolean = block == connectBlock && meta == world.getBlockMetadata(x, y, z)
}

