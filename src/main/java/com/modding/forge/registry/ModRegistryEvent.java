package com.modding.forge.registry;

import com.modding.forge.Main;
import com.modding.forge.capability.EntityStats;
import com.modding.forge.capability.storage.EntityStatsStorage;
import com.modding.forge.init.InitBlocks;
import com.modding.forge.init.InitItems;
import com.modding.forge.network.ModNetworkingManager;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@EventBusSubscriber
public class ModRegistryEvent
{
	@SubscribeEvent
	public static void onRegisterBlockEvent(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(InitBlocks.REGISTER_BLOCK.toArray(new Block[0]));
		TileEntityRegistry.register();
	}
	
	@SubscribeEvent
	public static void onRegisterItemEvent(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(InitItems.REGISTER_ITEMS.toArray(new Item[0]));
		event.getRegistry().registerAll(InitBlocks.REGISTER_ITEMBLOCK.toArray(new Item[0]));
	}
	
	public static void preInit(FMLPreInitializationEvent event)
	{
		
	}
	
	public static void init(FMLInitializationEvent event)
	{
		ModNetworkingManager.initialization();
		CapabilityManager.INSTANCE.register(EntityStats.class, new EntityStatsStorage(), EntityStats :: new);
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());
	}
	
	public static void postInit(FMLPostInitializationEvent event)
	{
		
	}
}
