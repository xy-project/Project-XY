package com.projextxy.mech.block

import com.projextxy.core.ProjectXYCore
import com.projextxy.core.blocks.BlockXy
import com.projextxy.core.blocks.glow.BlockXyGlow
import com.projextxy.core.blocks.traits.MachineBlock
import com.projextxy.core.handler.GuiHandler
import com.projextxy.core.reference.MCColors
import com.projextxy.mech.tile.TileFabricator
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.material.Material
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

class BlockFabricator extends BlockXyGlow(Material.rock) with ITileEntityProvider with MachineBlock {
  setBlockName("blockFabricator")
  colors = List(MCColors.BLUE)
  setHardness(1.0F)


  override def createNewTileEntity(world : World, meta : Int): TileEntity = new TileFabricator

  override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, par6: Int, par7: Float, par8: Float, par9: Float): Boolean = {
    if (!player.isSneaking && world.getTileEntity(x, y, z).isInstanceOf[TileFabricator]) {
      player.openGui(ProjectXYCore, GuiHandler.GuiIds.FABRICATOR.id, world, x, y, z)
    }
    true
  }
}
