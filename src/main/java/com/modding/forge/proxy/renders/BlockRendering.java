package com.modding.forge.proxy.renders;

import com.modding.forge.Reference;
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
		register(InitBlocks.bronze_block, 0, "bronze_block");
		register(InitBlocks.steel_block, 0, "steel_block");
		register(InitBlocks.titanium_block, 0, "titanium_block");
		register(InitBlocks.adamantium_block, 0, "adamantium_block");
	}
	
	public static void register(Block block, int meta, String model)
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(Reference.modID() + model, "inventory"));
	}
}
