package com.modding.forge.proxy.renders;

import com.modding.forge.Reference;
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
		register(InitItems.bronze_ingot, 0, "bronze_ingot");
		register(InitItems.steel_ingot, 0, "steel_ingot");
		register(InitItems.titanium_ingot, 0, "titanium_ingot");
		register(InitItems.adamantium_ingot, 0, "adamantium_ingot");
		register(InitItems.tungsten_ingot, 0, "tungsten_ingot");
		register(InitItems.mithril_ingot, 0, "mithril_ingot");
	}
	
	public static void register(Item item, int meta, String model)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Reference.modID() + model, "inventory"));
	}
}
