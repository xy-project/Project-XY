package com.projextxy.mech.block

import codechicken.lib.colour.ColourRGBA
import com.projextxy.core.ProjectXYCore
import com.projextxy.core.blocks.BlockXy
import com.projextxy.core.blocks.glow.TBlockXyGlow
import com.projextxy.core.blocks.traits.MachineBlock
import com.projextxy.core.client.render.block.RenderSimpleGlow
import com.projextxy.core.handler.GuiHandler
import com.projextxy.mech.block.BlockFabricator._
import com.projextxy.mech.multiblock.MultiBlockManager
import com.projextxy.mech.tile.TileFabricator
import net.minecraft.block.material.Material
import net.minecraft.block.{Block, ITileEntityProvider}
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.IIcon
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.common.util.ForgeDirection

class BlockFabricator extends BlockXy(Material.rock) with TBlockXyGlow with ITileEntityProvider with MachineBlock {
  setBlockName("blockFabricator")
  setHardness(1.0F)

  val icons = Array.fill[IIcon](2)(null)

  override def createNewTileEntity(world: World, meta: Int): TileEntity = new TileFabricator

  override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, par6: Int, par7: Float, par8: Float, par9: Float): Boolean = {
    for (direction <- ForgeDirection.VALID_DIRECTIONS) {
      MultiBlockManager.convertBlockToShadow(world, x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ)
    }
    if (!player.isSneaking && world.getTileEntity(x, y, z).isInstanceOf[TileFabricator]) {
      player.openGui(ProjectXYCore, GuiHandler.GuiIds.FABRICATOR.id, world, x, y, z)
    }
    true
  }

  override def registerBlockIcons(iconRegister: IIconRegister): Unit = {
    icons(0) = iconRegister.registerIcon(s"${ProjectXYCore.MOD_ID}:overlay/blockFabricatorTop")
    icons(1) = iconRegister.registerIcon(s"${ProjectXYCore.MOD_ID}:overlay/blockFabricatorSide")
  }

  override def getColor(meta: Int): Int = color

  override def getIcon(side: Int, meta: Int): IIcon = if (side == ForgeDirection.UP.ordinal()) icons(0) else icons(1)

  override def canConnectRedstone(world: IBlockAccess, x: Int, y: Int, z: Int, side: Int): Boolean = true

  override def getBrightness(world: IBlockAccess, x: Int, y: Int, z: Int): Int = {
    world.getTileEntity(x, y, z) match {
      case tileFab: TileFabricator =>
        tileFab.mode match {
          case 0 => if (tileFab.powered) 120 else 220
          case 1 => if (tileFab.powered) 220 else 120
          case 2 => if (tileFab.powered) 220 else 120
          case _ => 220
        }
      case _ => 220
    }
  }

  override def breakBlock(world: World, x: Int, y: Int, z: Int, block: Block, par6: Int): Unit = {
    world.getTileEntity(x, y, z).asInstanceOf[TileFabricator].dropItems()
    super.breakBlock(world, x, y, z, block, par6)
  }

  override def customDrops: Boolean = false

  override val hasColorMultiplier: Boolean = false

  override def getColor(iB4lockAccess: IBlockAccess, x: Int, y: Int, z: Int): Int = color
}

object BlockFabricator {
  lazy val color = new ColourRGBA(0, 100, 255, 255).rgb()
}
