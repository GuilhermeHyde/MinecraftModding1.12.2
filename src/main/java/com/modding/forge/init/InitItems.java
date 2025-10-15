package com.modding.forge.init;

import java.util.ArrayList;
import java.util.List;

import com.modding.forge.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class InitItems
{
	public static final List<Item> REGISTER_ITEMS = new ArrayList<Item>();
	public static Item bronze_ingot, steel_ingot, titanium_ingot, adamantium_ingot, tungsten_ingot, mithril_ingot, orichalcum_ingot;

	public static void initlaization()
	{
		bronze_ingot = register("bronze_ingot");
		steel_ingot = register("steel_ingot");
		titanium_ingot = register("titanium_ingot");
		tungsten_ingot = register("tungsten_ingot");
		orichalcum_ingot = register("orichalcum_ingot");
		mithril_ingot = register("mithril_ingot");
		adamantium_ingot = register("adamantium_ingot");
	}
	
	public static Item register(String name)
	{
		Item item = new Item().setUnlocalizedName(name).setRegistryName(Reference.location(name)).setCreativeTab(CreativeTabs.MATERIALS).setMaxStackSize(64);
		REGISTER_ITEMS.add(item);
		return item;
	}
}
