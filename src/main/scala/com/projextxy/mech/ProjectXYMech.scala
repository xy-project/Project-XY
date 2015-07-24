package com.projextxy.mech

import com.projextxy.mech.fmp.FMP
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}

@Mod(modid = "ProjectXyMech", name = "Project XY - Mech", modLanguage = "scala")
object ProjectXYMech {
  val MOD_ID = "ProjectXyMech"

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
