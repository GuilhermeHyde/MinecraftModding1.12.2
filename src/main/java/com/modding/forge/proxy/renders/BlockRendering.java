package com.modding.forge.proxy.renders;

import com.modding.forge.init.InitBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockRendering
{
	@SubscribeEvent
	public void onModelRegisterEvent(ModelRegistryEvent event)
	{
		register(InitBlocks.getBlockList());
	}
	
	private static void register(Block[] block)
	{
		for(int i = 0; i < block.length; i++)
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block[i]), 0, new ModelResourceLocation(block[i].getRegistryName(), "inventory"));
	}
}
