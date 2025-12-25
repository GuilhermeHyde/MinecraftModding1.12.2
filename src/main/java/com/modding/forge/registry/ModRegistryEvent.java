package com.modding.forge.registry;

import com.modding.forge.Main;
import com.modding.forge.init.InitBlocks;
import com.modding.forge.init.InitItems;
import com.modding.forge.network.ModNetworkingManager;
import com.modding.forge.capability.CapabilityAccessory;
import com.modding.forge.capability.CapabilityEquipment;
import com.modding.forge.capability.CapabilityLevel;
import com.modding.forge.capability.CapabilityStats;
import com.modding.forge.capability.CapabilityWeapon;
import com.modding.forge.capability.storage.CapabilityStatsStorage;
import com.modding.forge.capability.storage.CapabilityAccessoryStorage;
import com.modding.forge.capability.storage.CapabilityLevelStorage;
import com.modding.forge.capability.storage.CapabilityWeaponStorage;
import com.modding.forge.capability.storage.CapabilityEquipmentStorage;
import com.modding.forge.gui.GuiHandler;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
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
		event.getRegistry().registerAll(InitBlocks.getBlockList());
		TileEntityRegistry.register();
	}
	
	@SubscribeEvent
	public static void onRegisterItemEvent(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(InitItems.getItemList());
		event.getRegistry().registerAll(InitItems.getItemAccessoryList());
		event.getRegistry().registerAll(InitBlocks.getItemBlockList());
	}
	
	@SubscribeEvent
	public static void onTextureStitch(TextureStitchEvent.Pre event)
	{
	    event.getMap().registerSprite(new ResourceLocation("elders_reborn:gui/empty_ring"));
	    event.getMap().registerSprite(new ResourceLocation("elders_reborn:gui/empty_necklace"));
	}
	
	public static void preInit(FMLPreInitializationEvent event)
	{
		
	}
	
	public static void init(FMLInitializationEvent event)
	{
		ModNetworkingManager.initialization();
		CapabilityManager.INSTANCE.register(CapabilityStats.class, new CapabilityStatsStorage(), CapabilityStats :: new);
		CapabilityManager.INSTANCE.register(CapabilityLevel.class, new CapabilityLevelStorage(), CapabilityLevel :: new);
		CapabilityManager.INSTANCE.register(CapabilityAccessory.class, new CapabilityAccessoryStorage(), CapabilityAccessory :: new);
		CapabilityManager.INSTANCE.register(CapabilityWeapon.class, new CapabilityWeaponStorage(), CapabilityWeapon :: new);
		CapabilityManager.INSTANCE.register(CapabilityEquipment.class, new CapabilityEquipmentStorage(), CapabilityEquipment :: new);
		
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());
	}
	
	public static void postInit(FMLPostInitializationEvent event)
	{
		
	}
}
