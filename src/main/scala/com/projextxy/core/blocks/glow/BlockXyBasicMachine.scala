package com.projextxy.core.blocks.glow

import com.projextxy.core.ProjectXYCore
import com.projextxy.core.client.render.block.RenderSimpleGlow
import com.projextxy.core.reference.ModColors
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.init.Blocks
import net.minecraft.util.IIcon
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection

class BlockXyBasicMachine extends BlockXyGlow(Material.rock, RenderSimpleGlow.modelId) {
  setBlockName("blockXyBasicMachine")
  colors = ModColors.basicMachineColors
  val icons = Array.fill[IIcon](colors.length)(null)
  var upIcon: IIcon = null

  override def registerBlockIcons(iconRegister: IIconRegister): Unit = {
    def registerMachineIcon(name: String): IIcon = iconRegister.registerIcon(s"${ProjectXYCore.MOD_ID}:overlay/$name")
    icons(0) = registerMachineIcon("blockXyWater")
    icons(1) = registerMachineIcon("blockXyVoid")
    icons(2) = registerMachineIcon("blockXyIce")
    upIcon = registerMachineIcon("basicMachineTop")
  }


  override def onNeighborBlockChange(world: World, x: Int, y: Int, z: Int, block: Block): Unit = {
    world.getBlockMetadata(x, y, z) match {
      case 0 =>
        //Water
        //Not the best... But it works
        searchForBlockAndReplaceWith(world, x, y, z, Blocks.flowing_lava, Blocks.obsidian, Some(0))
        searchForBlockAndReplaceWith(world, x, y, z, Blocks.lava, Blocks.obsidian, Some(0))
        searchForBlockAndReplaceWith(world, x, y, z, Blocks.flowing_lava, Blocks.cobblestone, None)
        searchForBlockAndReplaceWith(world, x, y, z, Blocks.lava, Blocks.cobblestone, None)
      case 1 =>
      //Void
      case 2 =>
        //Ice
        searchForBlockAndReplaceWith(world, x, y, z, Blocks.water, Blocks.ice, None)
        searchForBlockAndReplaceWith(world, x, y, z, Blocks.flowing_water, Blocks.ice, None)
    }
  }

  def searchForBlockAndReplaceWith(world: World, x: Int, y: Int, z: Int, block: Block, replaceWith: Block, meta: Option[Int]): Unit = {
    for (direction <- ForgeDirection.values()) {
      val offsetBlock = world.getBlock(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ)
      if (offsetBlock == block)
        if (meta.isDefined) {
          if (world.getBlockMetadata(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ) == meta.get) {
            world.setBlock(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ, replaceWith)
          }
        } else {
          world.setBlock(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ, replaceWith)
        }

    }
  }


  override def getIcon(side: Int, meta: Int): IIcon = if (side == ForgeDirection.UP.ordinal()) upIcon else icons(meta)
}
