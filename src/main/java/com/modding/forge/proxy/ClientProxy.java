package com.modding.forge.proxy;

import com.modding.forge.event.ModEventHandler;
import com.modding.forge.proxy.renders.BlockRendering;
import com.modding.forge.proxy.renders.ItemRendering;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		register(new ItemRendering());
		register(new BlockRendering());
	}
	
	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
		ModKeybinds.initialization();
		register(new ModEventHandler());
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event)
	{
		super.postInit(event);
	}
}
