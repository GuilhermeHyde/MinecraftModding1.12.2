package com.modding.forge.init;

import com.modding.forge.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class InitItems
{
	public static Item bronze_ingot, steel_ingot, titanium_ingot, adamantium_ingot, tungsten_ingot, mithril_ingot;
	public static IForgeRegistry<Item> itemRegistry;
	
	public static void initlaization()
	{
		bronze_ingot = register("bronze_ingot", new Item()).setCreativeTab(CreativeTabs.MATERIALS);
		steel_ingot = register("steel_ingot", new Item()).setCreativeTab(CreativeTabs.MATERIALS);
		titanium_ingot = register("titanium_ingot", new Item()).setCreativeTab(CreativeTabs.MATERIALS);
		adamantium_ingot = register("adamantium_ingot", new Item()).setCreativeTab(CreativeTabs.MATERIALS);
		tungsten_ingot = register("tungsten_ingot", new Item()).setCreativeTab(CreativeTabs.MATERIALS);
		mithril_ingot = register("mithril_ingot", new Item()).setCreativeTab(CreativeTabs.MATERIALS);
	}
	
	public static Item register(String name, Item item)
	{
		item.setUnlocalizedName(name);
		itemRegistry.register(item.setRegistryName(Reference.location(name)));
		return item;
	}
}
