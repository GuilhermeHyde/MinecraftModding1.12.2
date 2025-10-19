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
	private final Map<ItemStack, Integer> castingList = Maps.<ItemStack, Integer>newHashMap();
	private final Map<ItemStack, Integer> meltingList = Maps.<ItemStack, Integer>newHashMap();
	private final Map<ItemStack, Integer> heatList = Maps.<ItemStack, Integer>newHashMap();
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();
	
	public static FusionFurnaceRecipe getInstance()
	{
		return INSTANCE;
	}
	
	private FusionFurnaceRecipe()
	{
		addFusionRecipes(new ItemStack(Items.IRON_INGOT), new ItemStack(Items.GOLD_INGOT), 1000, 300, 8, new ItemStack(InitItems.BRONZE_INGOT), 1.5F);
		addFusionRecipes(new ItemStack(Items.IRON_INGOT), new ItemStack(Items.COAL), 1530, 300, 8, new ItemStack(InitItems.STEEL_INGOT), 1.5F);
		addFusionRecipes(new ItemStack(InitItems.STEEL_INGOT), new ItemStack(InitItems.BRONZE_INGOT), 1725, 360, 10, new ItemStack(InitItems.TITANIUM_INGOT), 2.5F);
		addFusionRecipes(new ItemStack(InitItems.TITANIUM_INGOT), new ItemStack(Items.BLAZE_POWDER), 3422, 400, 16, new ItemStack(InitItems.TUNGSTEN_INGOT), 4.0F);
		addFusionRecipes(new ItemStack(InitItems.TITANIUM_INGOT), new ItemStack(Items.MAGMA_CREAM), 3422, 400, 16, new ItemStack(InitItems.ORICHALCUM_INGOT), 4.0F);
		addFusionRecipes(new ItemStack(InitItems.TUNGSTEN_INGOT), new ItemStack(Items.DRAGON_BREATH), 5000, 480, 22, new ItemStack(InitItems.ADAMANTIUM_INGOT), 5.5F);
		addFusionRecipes(new ItemStack(InitItems.ORICHALCUM_INGOT), new ItemStack(Items.DRAGON_BREATH), 5000, 480, 22, new ItemStack(InitItems.MITHRIL_INGOT), 5.5F);
	}
	
	private void addFusionRecipes(ItemStack input1, ItemStack input2, int heat, int melting, int casting, ItemStack result, float exp)
	{
		if(getRecipesResult(input1, input2, heat) != ItemStack.EMPTY) return;
		this.recipesList.put(input1, input2, result);
		this.castingList.put(result, casting);
		this.meltingList.put(result, melting);
		this.heatList.put(result, heat);
		this.experienceList.put(result, Float.valueOf(exp));
	}
	
	public ItemStack getRecipesResult(ItemStack input1, ItemStack input2, int heat)
	{
		for(Entry<ItemStack, Map<ItemStack, ItemStack>> recipe : recipesList.rowMap().entrySet())
		{
			for(Entry<ItemStack, ItemStack> entry : recipe.getValue().entrySet())
			{
				boolean flag = heat >= this.getMeltingPoint(entry.getValue());
				
				if(this.compareItemStack(input1, (ItemStack)recipe.getKey()) || this.compareItemStack(input2, (ItemStack)recipe.getKey()))
				{
					if(flag && this.compareItemStack(input2, entry.getKey()) || this.compareItemStack(input1, (ItemStack)entry.getKey())) return entry.getValue();
				}
			}
		}
		return ItemStack.EMPTY;
	}
	
	public ItemStack isResult(ItemStack input)
	{
		for(Entry<ItemStack, Map<ItemStack, ItemStack>> recipe : recipesList.rowMap().entrySet())
		{
			for(Entry<ItemStack, ItemStack> entry : recipe.getValue().entrySet())
			{
				if(this.compareItemStack(input, (ItemStack)recipe.getKey()))
				{
					return entry.getKey();
				}
				else if(this.compareItemStack(input, (ItemStack)entry.getKey()))
				{
					return recipe.getKey();
				}
			}
		}
		return ItemStack.EMPTY;
	}
	
	public boolean compareItemStack(ItemStack input, ItemStack key)
	{
		return ItemStack.areItemsEqual(key, input) && (key.getMetadata() == 32767 || key.getMetadata() == input.getMetadata());
	}
	
	public int getMeltingPoint(ItemStack input1)
	{
		for(Entry<ItemStack, Integer> entry : this.heatList.entrySet())
		{
			if(this.compareItemStack(input1, entry.getKey())) return entry.getValue().intValue();
		}
		return 0;
	}
	
	public float getCraftingExp(ItemStack input1)
	{
		for(Entry<ItemStack, Float> entry : this.experienceList.entrySet())
		{
			if(this.compareItemStack(input1, entry.getKey())) return entry.getValue().floatValue();
		}
		return 0.0F;
	}
	
	public int getMeltingProcess(ItemStack input1)
	{
		for(Entry<ItemStack, Integer> entry : this.meltingList.entrySet())
		{
			if(this.compareItemStack(input1, entry.getKey())) return entry.getValue().intValue();
		}
		return 0;
	}
	
	public int getCastingProcess(ItemStack input1)
	{
		for(Entry<ItemStack, Integer> entry : this.castingList.entrySet())
		{
			if(this.compareItemStack(input1, entry.getKey())) return entry.getValue().intValue();
		}
		return 0;
	}
}
