package com.modding.forge.init;

import java.util.ArrayList;
import java.util.List;

import com.modding.forge.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class InitItems
{
	public static final List<Item> REGISTER_ITEMS = new ArrayList<Item>();
	
	public static final Item BRONZE_INGOT = createItem("bronze_ingot");
	public static final Item STEEL_INGOT = createItem("steel_ingot");
	public static final Item TITANIUM_INGOT = createItem("titanium_ingot");
	public static final Item ADAMANTIUM_INGOT = createItem("adamantium_ingot");
	public static final Item TUNGSTEN_INGOT = createItem("tungsten_ingot");
	public static final Item MITHRIL_INGOT = createItem("mithril_ingot");
	public static final Item ORICHALCUM_INGOT = createItem("orichalcum_ingot");;
	
	private static Item createItem(String name)
	{
		Item item = new Item();
		item.setUnlocalizedName(name);
		item.setRegistryName(Reference.location(name));
		item.setCreativeTab(CreativeTabs.MATERIALS);
		item.setMaxStackSize(64);
		
		REGISTER_ITEMS.add(item);
		return item;
	}
}
