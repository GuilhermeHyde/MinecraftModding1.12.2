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
		register(InitItems.getItemList());
		register(InitItems.getItemAccessoryList());
	}
	
	private static void register(Item[] item)
	{
		for(int i = 0; i < item.length; i++)
		{
			ModelLoader.setCustomModelResourceLocation(item[i], 0, new ModelResourceLocation(item[i].getRegistryName(), "inventory"));
		}
	}
}
