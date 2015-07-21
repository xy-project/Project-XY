package com.projextxy.mech

import com.projextxy.mech.fmp.FMP
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}

@Mod(modid = "ProjectXyMech", name = "Project XY - Mech", modLanguage = "scala")
object ProjectXYEnergy {
  val MOD_ID = "ProjectXyMech"

  @EventHandler def init(event: FMLInitializationEvent) {
    FMP.init()
    ProjectXYEnergyProxy.init(event)
  }

  @EventHandler def preInit(event: FMLPreInitializationEvent) {
    ProjectXYEnergyProxy.preInit(event)
  }


  @EventHandler def postInit(event: FMLPostInitializationEvent) {
    ProjectXYEnergyProxy.postInit(event)
  }
}
