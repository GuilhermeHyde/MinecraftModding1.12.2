package com.modding.forge.blocks.recipes;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.modding.forge.init.InitItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class FusionFurnaceRecipe
{
	private static final FusionFurnaceRecipe INSTANCE = new FusionFurnaceRecipe();
	private final Table<ItemStack, ItemStack, ItemStack> recipesList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
	private final Map<ItemStack, Integer> heatList = Maps.<ItemStack, Integer>newHashMap();
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();
	
	public static FusionFurnaceRecipe getInstance()
	{
		return INSTANCE;
	}
	
	private FusionFurnaceRecipe()
	{
		addFusionRecipes(new ItemStack(Items.IRON_INGOT), new ItemStack(Items.GOLD_INGOT), 1000, new ItemStack(InitItems.bronze_ingot), 1.0F);
		addFusionRecipes(new ItemStack(Items.IRON_INGOT), new ItemStack(Items.COAL), 1530, new ItemStack(InitItems.steel_ingot), 1.0F);
		addFusionRecipes(new ItemStack(InitItems.steel_ingot), new ItemStack(InitItems.bronze_ingot), 1725, new ItemStack(InitItems.titanium_ingot), 1.0F);
		addFusionRecipes(new ItemStack(InitItems.titanium_ingot), new ItemStack(Items.BLAZE_POWDER), 3422, new ItemStack(InitItems.tungsten_ingot), 1.0F);
		addFusionRecipes(new ItemStack(InitItems.titanium_ingot), new ItemStack(Items.MAGMA_CREAM), 3880, new ItemStack(InitItems.orichalcum_ingot), 1.0F);
		addFusionRecipes(new ItemStack(InitItems.tungsten_ingot), new ItemStack(Items.CHORUS_FRUIT_POPPED), 5000, new ItemStack(InitItems.adamantium_ingot), 1.0F);
		addFusionRecipes(new ItemStack(InitItems.orichalcum_ingot), new ItemStack(Items.CHORUS_FRUIT_POPPED), 4875, new ItemStack(InitItems.mithril_ingot), 1.0F);
	}
	
	private void addFusionRecipes(ItemStack input1, ItemStack input2, int heat, ItemStack result, float exp)
	{
		if(getRecipesResult(input1, input2, heat) != ItemStack.EMPTY) return;
		this.recipesList.put(input1, input2, result);
		this.heatList.put(result, Integer.valueOf(heat));
		this.experienceList.put(result, Float.valueOf(exp));
	}
	
	public ItemStack getRecipesResult(ItemStack input1, ItemStack input2, int heat)
	{
		for(Entry<ItemStack, Map<ItemStack, ItemStack>> recipe : recipesList.rowMap().entrySet())
		{
			for(Entry<ItemStack, Integer> value : heatList.entrySet())
			{
				if(heat >= value.getValue())
				{
					if(this.compareItemStack(input1, (ItemStack)recipe.getKey()))
					{
						for(Entry<ItemStack, ItemStack> entry : recipe.getValue().entrySet())
						{
							if(this.compareItemStack(input2, entry.getKey())) return entry.getValue();
						}
					}
					else if(this.compareItemStack(input2, (ItemStack)recipe.getKey()))
					{
						for(Entry<ItemStack, ItemStack> entry : recipe.getValue().entrySet())
						{
							if(this.compareItemStack(input1, entry.getKey())) return entry.getValue();
						}
					}
				}
			}
		}
		return ItemStack.EMPTY;
	}
	
	private boolean compareItemStack(ItemStack input1, ItemStack key1)
	{
		return key1.getItem() == input1.getItem() && (key1.getMetadata() == 32767 || key1.getMetadata() == input1.getMetadata());
	}
}
