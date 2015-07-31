package com.projextxy.mech

import com.projextxy.mech.fmp.FMP
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}

@Mod(modid = "ProjectXYMech", name = "Project XY - Mech", modLanguage = "scala", dependencies = "required-after:ProjectXY;required-after:ForgeMultipart")
object ProjectXYMech {
  val MOD_ID = "ProjectXYMech"

  @EventHandler def init(event: FMLInitializationEvent) {
    FMP.init()
    ProjectXYMechProxy.init(event)
  }

  @EventHandler def preInit(event: FMLPreInitializationEvent) {
    ProjectXYMechProxy.preInit(event)
  }


  @EventHandler def postInit(event: FMLPostInitializationEvent) {
    ProjectXYMechProxy.postInit(event)
  }
}
