package com.modding.forge.registry;

import com.modding.forge.init.InitBlocks;
import com.modding.forge.init.InitItems;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModRegistryEvent
{
	@SubscribeEvent
	public void onRegisterBlockEvent(RegistryEvent.Register<Block> event)
	{
		InitBlocks.registerBlock(event.getRegistry());
	}
	
	@SubscribeEvent
	public void onRegisterItemEvent(RegistryEvent.Register<Item> event)
	{
		InitBlocks.registerBlockItem(event.getRegistry());
		InitItems.itemRegistry = event.getRegistry();
		InitItems.initlaization();
	}
}
