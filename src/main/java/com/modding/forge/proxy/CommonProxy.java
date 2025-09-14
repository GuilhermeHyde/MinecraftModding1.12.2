package com.modding.forge.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy implements IProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		
	}

	@Override
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
	
	public static void register(Object event)
	{
		MinecraftForge.EVENT_BUS.register(event);
	}
}
