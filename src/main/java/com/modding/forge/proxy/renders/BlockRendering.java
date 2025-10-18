package com.modding.forge.proxy.renders;

import java.util.List;

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
		List<Block> blockList = InitBlocks.REGISTER_BLOCK;
		for(int i = 0; i < blockList.size(); i++)
		{
			if(!blockList.isEmpty()) register(blockList.get(i));
		}
	}
	
	private static void register(Block block)
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
}
