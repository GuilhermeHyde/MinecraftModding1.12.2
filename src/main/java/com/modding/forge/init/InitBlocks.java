package com.modding.forge.init;

import java.util.ArrayList;
import java.util.List;

import com.modding.forge.Reference;
import com.modding.forge.blocks.FusionFurnaceBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class InitBlocks
{
	private static final List<Block> REGISTER_BLOCK = new ArrayList<Block>();
	private static final List<Item> REGISTER_ITEMBLOCK = new ArrayList<Item>();
	
	public static final Block BRONZE_BLOCK = createBlock("bronze_block", Material.IRON);
	public static final Block STEEL_BLOCK = createBlock("steel_block", Material.IRON);
	public static final Block TITANIUM_BLOCK = createBlock("titanium_block", Material.IRON);
	public static final Block ADAMANTIUM_BLOCK = createBlock("adamantium_block", Material.IRON);
	public static final Block TUNGSTEN_BLOCK = createBlock("tungsten_block", Material.IRON);
	public static final Block MITHRIL_BLOCK = createBlock("mithril_block", Material.IRON);
	public static final Block ORICHALCUM_BLOCK = createBlock("orichalcum_block", Material.IRON);
	public static final Block FUSION_FURNACE = createFusionFurnace("fusion_furnace");
	
	private static Block createBlock(String name, Material material)
	{
		Block block = new Block(material);
		block.setUnlocalizedName(name);
		block.setRegistryName(Reference.location(name));
		block.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		
		Item itemBlock = new ItemBlock(block);
		itemBlock.setRegistryName(Reference.location(name));
		
		REGISTER_BLOCK.add(block);
		REGISTER_ITEMBLOCK.add(itemBlock);
		return block;
	}
	
	private static Block createFusionFurnace(String name)
	{
		Block block = new FusionFurnaceBlock();
		block.setUnlocalizedName(name);
		block.setRegistryName(Reference.location(name));
		block.setCreativeTab(CreativeTabs.DECORATIONS);
		
		Item itemBlock = new ItemBlock(block);
		itemBlock.setRegistryName(Reference.location(name));
		
		REGISTER_BLOCK.add(block);
		REGISTER_ITEMBLOCK.add(itemBlock);
		return block;
	}
	
	public static Block[] getBlockList()
	{
		return REGISTER_BLOCK.toArray(new Block[0]);
	}
	
	public static Item[] getItemBlockList()
	{
		return REGISTER_ITEMBLOCK.toArray(new Item[0]);
	}
}
