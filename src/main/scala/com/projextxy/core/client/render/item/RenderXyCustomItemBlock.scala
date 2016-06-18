package com.projextxy.core.client.render.item

import codechicken.lib.colour.ColourRGBA
import codechicken.lib.vec.Vector3
import com.projextxy.core.ProjectXYCoreProxy
import com.projextxy.core.blocks.glow.{BlockXyColorizer, BlockXyCustom}
import com.projextxy.core.blocks.traits.{TColorBlock, TConnectedTextureBlock}
import com.projextxy.core.client.render.block.RenderBlock
import com.projextxy.core.client.{CTRegistry, RenderTickHandler}
import com.projextxy.lib.cofh.RenderHelper
import net.minecraft.block.Block
import net.minecraft.client.renderer.RenderBlocks
import net.minecraft.item.ItemStack
import net.minecraftforge.client.IItemRenderer
import net.minecraftforge.client.IItemRenderer.{ItemRenderType, ItemRendererHelper}
import org.lwjgl.opengl.GL11

class RenderXyCustomItemBlock extends IItemRenderer {
  override def handleRenderType(item: ItemStack, renderType: ItemRenderType): Boolean = true

  override def shouldUseRenderHelper(renderType: ItemRenderType, item: ItemStack, helper: ItemRendererHelper): Boolean = true

  override def renderItem(renderType: ItemRenderType, item: ItemStack, data: AnyRef*) = {
    var r: Int = 255
    var g: Int = 255
    var b: Int = 255

    if (item.stackTagCompound != null) {
      if (item.stackTagCompound.hasKey("lsd")) {
        r = RenderTickHandler.r
        g = RenderTickHandler.g
        b = RenderTickHandler.b
      } else {
        r = item.stackTagCompound.getInteger("r")
        g = item.stackTagCompound.getInteger("g")
        b = item.stackTagCompound.getInteger("b")
      }

    }

    def offset: Double = {
      renderType match {
        case ItemRenderType.EQUIPPED_FIRST_PERSON => 0
        case ItemRenderType.EQUIPPED => 0
        case _ => -0.5
      }
    }

    def colorMultiplier: Int = {
      Block.getBlockFromItem(item.getItem) match {
        case colorMult: BlockXyCustom =>
          colorMult.sub_blocks(item.getItemDamage) match {
            case colorMultiplier: TColorBlock => if(colorMultiplier.hasColorMultiplier) new ColourRGBA(r, g, b, 255).rgb() else new ColourRGBA(255, 255, 255, 255).rgb()
            case _ => new ColourRGBA(255, 255, 255, 255).rgb()
          }
        case _ => new ColourRGBA(255, 255, 255, 255).rgb()
      }
    }

    val d: Vector3 = new Vector3
    if (renderType == ItemRenderType.EQUIPPED_FIRST_PERSON && renderType == ItemRenderType.EQUIPPED) d.add(-.5, -.5, -.5)

    GL11.glPushAttrib(GL11.GL_ENABLE_BIT)
    GL11.glEnable(GL11.GL_BLEND)
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    RenderHelper.renderTextureAsBlock(data(0).asInstanceOf[RenderBlocks], ProjectXYCoreProxy.animationFx.texture, offset, offset, offset, new ColourRGBA(r, g, b, 255).rgb())

    Block.getBlockFromItem(item.getItem) match {
      case colorMult: BlockXyCustom =>
        colorMult.sub_blocks(item.getItemDamage) match {
          case connected: TConnectedTextureBlock =>
            if (connected.renderBlockTexture) {
              GL11.glTranslated(0.5, 0.5, 0.5)
              RenderBlock.renderStandardInvBlock(data(0).asInstanceOf[RenderBlocks], connected, item.getItemDamage)
              GL11.glTranslated(-0.5, -0.5, -0.5)
            }
            RenderHelper.renderTextureAsBlock(
              data(0).asInstanceOf[RenderBlocks],
              CTRegistry.getTexture(connected.connectedFolder).icons(0),
              offset,
              offset,
              offset,
              new ColourRGBA(255, 255, 255, 255).rgb())
          case _ =>
            RenderHelper.renderTextureAsBlock(data(0).asInstanceOf[RenderBlocks], Block.getBlockFromItem(item.getItem), item.getItemDamage, offset, offset, offset, colorMultiplier)
        }
      case colorizer: BlockXyColorizer =>
        GL11.glTranslated(0.5, 0.5, 0.5)
        RenderBlock.renderStandardInvBlock(data(0).asInstanceOf[RenderBlocks], colorizer, item.getItemDamage)
        GL11.glTranslated(-0.5, -0.5, -0.5)
    }
    GL11.glPopAttrib()
  }

}
