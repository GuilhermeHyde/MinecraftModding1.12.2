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
		register(InitBlocks.BRONZE_BLOCK, 0, "bronze_block");
		register(InitBlocks.STEEL_BLOCK, 0, "steel_block");
		register(InitBlocks.TITANIUM_BLOCK, 0, "titanium_block");
		register(InitBlocks.ADAMANTIUM_BLOCK, 0, "adamantium_block");
		register(InitBlocks.TUNGSTEN_BLOCK, 0, "tungsten_block");
		register(InitBlocks.MITHRIL_BLOCK, 0, "mithril_block");
		register(InitBlocks.ORICHALCUM_BLOCK, 0, "orichalcum_block");
		register(InitBlocks.FUSION_FURNACE, 0, "fusion_furnace");
	}
	
	public static void register(Block block, int meta, String model)
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(Reference.modID() + model, "inventory"));
	}
}
