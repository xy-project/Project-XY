package com.projextxy.core.blocks.glow

import com.projextxy.core.blocks.BlockXy
import com.projextxy.core.blocks.traits.MachineBlock
import com.projextxy.core.handler.GuiHandler
import com.projextxy.core.tile.TileColorizer
import com.projextxy.core.{CoreBlocks, ProjectXYCore}
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.EnumCreatureType
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.IIcon
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.common.util.ForgeDirection

class BlockXyColorizer extends BlockXy(Material.rock) with MachineBlock {
  setBlockName("blockXyColorizer")
  setHardness(1.0f)

  override def getIcon(side: Int, meta: Int): IIcon = {
    ForgeDirection.getOrientation(side) match {
      case ForgeDirection.UP => blockIcon
      case _ => CoreBlocks.blockXyStorage.getIcon(side, meta)
    }
  }

  override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, par6: Int, par7: Float, par8: Float, par9: Float): Boolean = {
    if (!player.isSneaking && world.getTileEntity(x, y, z).isInstanceOf[TileColorizer]) {
      player.openGui(ProjectXYCore, GuiHandler.GuiIds.RGB.id, world, x, y, z)
    }
    true
  }

  override def registerBlockIcons(iconRegister: IIconRegister) = {
    blockIcon = iconRegister.registerIcon(s"${ProjectXYCore.MOD_ID}:overlay/$getTextureName")
  }

  override def createNewTileEntity(world: World, meta: Int): TileEntity = new TileColorizer

  override def canCreatureSpawn(`type`: EnumCreatureType, world: IBlockAccess, x: Int, y: Int, z: Int): Boolean = false
}
