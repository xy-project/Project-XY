package com.projextxy.mech.multiblock

import codechicken.lib.vec.BlockCoord
import com.projextxy.core.ProjectXYCore
import com.projextxy.core.blocks.BlockXy
import com.projextxy.core.blocks.glow.TBlockXyGlow
import com.projextxy.core.reference.{MCColors, ModColors}
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.{IBlockAccess, World}

class BlockMultiTank extends BlockXy(Material.rock) with TBlockXyGlow with BlockMulti {
  setBlockName("blockMultiTank")

  override def createNewTileEntity(world: World, meta: Int): TileEntity = new TileValve

  override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, par6: Int, par7: Float, par8: Float, par9: Float): Boolean = {
    world.getTileEntity(x, y, z) match {
      case valve: TileValve =>
        if (!world.isRemote) {
          if (valve.isAlreadyPartOfStructure)
            return super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9)
          MultiTankInitiator.create(world, new BlockCoord(x, y, z))
        }
      case _ =>
    }
    false
  }

  override def registerBlockIcons(iconRegister: IIconRegister): Unit = {
    blockIcon = iconRegister.registerIcon(s"${ProjectXYCore.MOD_ID}:overlay/machineValve")
  }

  override def getColor(meta: Int): Int = ModColors.machineColor.rgb()

  override def getColor(world: IBlockAccess, x: Int, y: Int, z: Int): Int = ModColors.machineColor.rgb()

  override val hasColorMultiplier: Boolean = false
}

