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
		event.getRegistry().registerAll(InitBlocks.REGISTER_BLOCK.toArray(new Block[0]));
		TileEntityRegistry.register();
	}
	
	@SubscribeEvent
	public void onRegisterItemEvent(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(InitItems.REGISTER_ITEMS.toArray(new Item[0]));
		event.getRegistry().registerAll(InitBlocks.REGISTER_ITEMBLOCK.toArray(new Item[0]));
	}
}
