package com.projextxy.core.resource

import com.projextxy.core.ProjectXYCore
import cpw.mods.fml.relauncher.ReflectionHelper
import net.minecraft.client.particle.EffectRenderer
import net.minecraft.util.ResourceLocation

object ResourceLib {
  val beamTexture = register("textures/fx/beam.png")
  val baseGui = register("textures/gui/guiBase.png")
  val blockSheet = new ResourceAction(new ResourceLocation("textures/atlas/blocks.png"))
  val itemSheet = new ResourceAction(new ResourceLocation("textures/atlas/items.png"))

  def particleSheet: ResourceAction = new ResourceAction(ReflectionHelper.getPrivateValue(classOf[EffectRenderer], null, "particleTextures", "b", "field_110737_b").asInstanceOf[ResourceLocation])

  def register(path: String) = new ResourceAction(new ResourceLocation(ProjectXYCore.MOD_ID.toLowerCase, path))
}
