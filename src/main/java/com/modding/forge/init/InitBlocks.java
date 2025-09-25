package com.modding.forge.init;

import com.modding.forge.Reference;
import com.modding.forge.blocks.FusionFurnaceBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;

public class InitBlocks
{
	public static Block bronze_block, steel_block, titanium_block, adamantium_block, tungsten_block, mithril_block,
						fusion_furnace;
	
	public static Block[] listBlock = new Block[7];
	public static Item[] listBlockItem = new Item[7];
	public static int blockID;
	
	public static void initlaization()
	{
		bronze_block = register("bronze_block", new Block(Material.IRON)).setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		steel_block = register("steel_block", new Block(Material.IRON)).setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		adamantium_block = register("adamantium_block", new Block(Material.IRON)).setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		titanium_block = register("titanium_block", new Block(Material.IRON)).setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		tungsten_block = register("tungsten_block", new Block(Material.IRON)).setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		mithril_block = register("mithril_block", new Block(Material.IRON)).setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		
		fusion_furnace = register("fusion_furnace", new FusionFurnaceBlock()).setCreativeTab(CreativeTabs.DECORATIONS);
	}
	
	public static Block register(String name, Block block)
	{
		block.setUnlocalizedName(name);
		listBlock[blockID] = block.setRegistryName(Reference.location(name));
		listBlockItem[blockID] = new ItemBlock(block).setRegistryName(Reference.location(name));
		blockID++;
		return block;
	}
	
	public static void registerBlock(IForgeRegistry<Block> event)
	{
		event.registerAll(listBlock);
	}
	
	public static void registerBlockItem(IForgeRegistry<Item> event)
	{
		event.registerAll(listBlockItem);
	}
}
