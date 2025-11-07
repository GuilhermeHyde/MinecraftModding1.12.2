package com.modding.forge.proxy.renders;

import java.util.List;

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
		List<Item> itemList = InitItems.REGISTER_ITEMS;
		for(int i = 0; i < itemList.size(); i++)
		{
			if(!itemList.isEmpty()) register(itemList.get(i));
		}
		
		List<Item> accessoryList = InitItems.REGISTER_ACCESSORY;
		for(int j = 0; j < accessoryList.size(); j++)
		{
			if(!accessoryList.isEmpty()) register(accessoryList.get(j));
		}
	}
	
	private static void register(Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
