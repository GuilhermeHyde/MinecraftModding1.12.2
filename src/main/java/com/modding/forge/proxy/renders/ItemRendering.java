package com.modding.forge.proxy.renders;

import com.modding.forge.init.InitItems;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemRendering
{
	@SubscribeEvent
	public void onModelRegisterEvent(ModelRegistryEvent event)
	{
		register(InitItems.BRONZE_INGOT, 0, "bronze_ingot");
		register(InitItems.STEEL_INGOT, 0, "steel_ingot");
		register(InitItems.TITANIUM_INGOT, 0, "titanium_ingot");
		register(InitItems.ADAMANTIUM_INGOT, 0, "adamantium_ingot");
		register(InitItems.TUNGSTEN_INGOT, 0, "tungsten_ingot");
		register(InitItems.MITHRIL_INGOT, 0, "mithril_ingot");
		register(InitItems.ORICHALCUM_INGOT, 0, "orichalcum_ingot");
	}
	
	public static void register(Item item, int meta, String model)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
