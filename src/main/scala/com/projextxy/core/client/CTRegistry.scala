package com.projextxy.core.client

import codechicken.lib.render.TextureUtils
import codechicken.lib.render.TextureUtils.IIconSelfRegister
import com.projextxy.core.ProjectXYCore
import com.projextxy.core.client.render.connected.IconConnectedTexture
import net.minecraft.block.Block
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object CTRegistry extends IIconSelfRegister {
  val CLEAN_CONNECTED_FOLDER = "cleanConnected"
  val XY_MACHINE_FOLDER = "xyMachine"
  val XY_MACHINE_WHITE_FOLDER = "xyMachineWhite"

  val ctms = mutable.HashMap[String, IconConnectedTexture]()
  val folders = new ArrayBuffer[String]()

  def init(): Unit = {
    addConnectedTexture(CLEAN_CONNECTED_FOLDER)
    addConnectedTexture(XY_MACHINE_FOLDER)
    addConnectedTexture(XY_MACHINE_WHITE_FOLDER)

    TextureUtils.addIconRegistrar(this)
  }

  private def addConnectedTexture(folder: String): Unit = folders += folder

  override def atlasIndex(): Int = 0

  override def registerIcons(reg: IIconRegister): Unit = {
    for (folder <- folders) {
      val icons = Array.fill[IIcon](16)(null)
      ctms.put(folder, new IconConnectedTexture(reg, ProjectXYCore.MOD_ID + ":overlay/" + folder + "/" + folder))
    }
  }

  def getTexture(folder: String): IconConnectedTexture = ctms.get(folder).get

  def shouldConnectToBlock(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, meta: Int, connectBlock: Block): Boolean = block == connectBlock && meta == world.getBlockMetadata(x, y, z)
}

