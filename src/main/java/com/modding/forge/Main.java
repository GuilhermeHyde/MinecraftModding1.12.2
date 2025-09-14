package com.modding.forge;

import com.modding.forge.proxy.IProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Main.MODID, version = Main.VERSION, name = Main.NAME)
public class Main
{
	public static final String MODID = "elders_reborn";
	public static final String VERSION = "1.0";
	public static final String NAME = "Elders Reborn";
	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide = "com.modding.forge.proxy.ClientProxy", serverSide = "com.modding.forge.proxy.CommonProxy")
	public static IProxy proxy;
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit(event);
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
		proxy.init(event);
	}
	
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}
}
