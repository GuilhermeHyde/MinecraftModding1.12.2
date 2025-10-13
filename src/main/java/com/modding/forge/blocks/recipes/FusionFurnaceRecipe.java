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
		addFusionRecipes(new ItemStack(Items.IRON_INGOT), new ItemStack(Items.GOLD_INGOT), 1000, 200, 8, new ItemStack(InitItems.bronze_ingot), 1.0F);
		addFusionRecipes(new ItemStack(Items.IRON_INGOT), new ItemStack(Items.COAL), 1530, 200, 8, new ItemStack(InitItems.steel_ingot), 1.0F);
		addFusionRecipes(new ItemStack(InitItems.steel_ingot), new ItemStack(InitItems.bronze_ingot), 1725, 300, 10, new ItemStack(InitItems.titanium_ingot), 1.0F);
		addFusionRecipes(new ItemStack(InitItems.titanium_ingot), new ItemStack(Items.BLAZE_POWDER), 3422, 400, 18, new ItemStack(InitItems.tungsten_ingot), 1.0F);
		addFusionRecipes(new ItemStack(InitItems.titanium_ingot), new ItemStack(Items.MAGMA_CREAM), 3880, 360, 14, new ItemStack(InitItems.orichalcum_ingot), 1.0F);
		addFusionRecipes(new ItemStack(InitItems.tungsten_ingot), new ItemStack(Items.DRAGON_BREATH), 5000, 480, 26, new ItemStack(InitItems.adamantium_ingot), 1.0F);
		addFusionRecipes(new ItemStack(InitItems.orichalcum_ingot), new ItemStack(Items.DRAGON_BREATH), 4875, 460, 22, new ItemStack(InitItems.mithril_ingot), 1.0F);
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
				
				if(this.compareItemStack(input1, (ItemStack)recipe.getKey()))
				{
					if(this.compareItemStack(input2, entry.getKey()) && flag) return entry.getValue();
				}
				else if(this.compareItemStack(input2, (ItemStack)recipe.getKey()))
				{
					if(this.compareItemStack(input1, entry.getKey()) && flag) return entry.getValue();
				}
			}
		}
		return ItemStack.EMPTY;
	}
	
	public Table<ItemStack, ItemStack, ItemStack> getMaterialRecipe()
	{
		return this.recipesList;
	}
	
	public boolean compareItemStack(ItemStack input1, ItemStack key1)
	{
		return key1.getItem() == input1.getItem() && (key1.getMetadata() == 32767 || key1.getMetadata() == input1.getMetadata());
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
