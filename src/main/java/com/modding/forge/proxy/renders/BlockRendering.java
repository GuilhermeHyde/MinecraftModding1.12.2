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
		register(InitBlocks.tungsten_block, 0, "tungsten_block");
		register(InitBlocks.mithril_block, 0, "mithril_block");
		register(InitBlocks.orichalcum_block, 0, "orichalcum_block");
		register(InitBlocks.fusion_furnace, 0, "fusion_furnace");
	}
	
	public static void register(Block block, int meta, String model)
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(Reference.modID() + model, "inventory"));
	}
}
