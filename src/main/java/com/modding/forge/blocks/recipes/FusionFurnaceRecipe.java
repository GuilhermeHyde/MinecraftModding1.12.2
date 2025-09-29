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
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();
	
	public static FusionFurnaceRecipe getInstance()
	{
		return INSTANCE;
	}
	
	private FusionFurnaceRecipe()
	{
		addFusionRecipes(new ItemStack(Items.IRON_INGOT), new ItemStack(Items.GOLD_INGOT), new ItemStack(InitItems.bronze_ingot), 1.0F);
		addFusionRecipes(new ItemStack(Items.IRON_INGOT), new ItemStack(Items.COAL), new ItemStack(InitItems.steel_ingot), 1.0F);
		addFusionRecipes(new ItemStack(InitItems.steel_ingot), new ItemStack(InitItems.bronze_ingot), new ItemStack(InitItems.titanium_ingot), 1.0F);
		addFusionRecipes(new ItemStack(InitItems.titanium_ingot), new ItemStack(Items.BLAZE_POWDER), new ItemStack(InitItems.tungsten_ingot), 1.0F);
		addFusionRecipes(new ItemStack(InitItems.titanium_ingot), new ItemStack(Items.MAGMA_CREAM), new ItemStack(InitItems.orichalcum_ingot), 1.0F);
		addFusionRecipes(new ItemStack(InitItems.tungsten_ingot), new ItemStack(Items.CHORUS_FRUIT_POPPED), new ItemStack(InitItems.adamantium_ingot), 1.0F);
		addFusionRecipes(new ItemStack(InitItems.orichalcum_ingot), new ItemStack(Items.CHORUS_FRUIT_POPPED), new ItemStack(InitItems.mithril_ingot), 1.0F);
	}
	
	private void addFusionRecipes(ItemStack input1, ItemStack input2, ItemStack result, float exp)
	{
		if(getRecipesResult(input1, input2) != ItemStack.EMPTY) return;
		this.recipesList.put(input1, input2, result);
		this.experienceList.put(result, Float.valueOf(exp));
	}
	
	public ItemStack getRecipesResult(ItemStack input1, ItemStack input2)
	{
		for(Entry<ItemStack, Map<ItemStack, ItemStack>> recipe : recipesList.columnMap().entrySet())
		{
			if(compareItemStack(input1, (ItemStack)recipe.getKey()))
			{
				for(Entry<ItemStack, ItemStack> entry : recipe.getValue().entrySet())
				{
					if(compareItemStack(input2, (ItemStack)entry.getKey()))
					{
						return (ItemStack)entry.getValue();
					}
				}
			}
			else if(compareItemStack(input2, (ItemStack)recipe.getKey()))
			{
				for(Entry<ItemStack, ItemStack> entry : recipe.getValue().entrySet())
				{
					if(compareItemStack(input1, (ItemStack)entry.getKey()))
					{
						return (ItemStack)entry.getValue();
					}
				}
			}
		}
		return ItemStack.EMPTY;
	}
	
	private boolean compareItemStack(ItemStack input1, ItemStack input2)
	{
		return input2.getItem() == input1.getItem() && (input2.getMetadata() == 32767 || input2.getMetadata() == input1.getMetadata());
	}
	
	
}
