package com.modding.forge.init;

import java.util.ArrayList;
import java.util.List;

import com.modding.forge.Reference;
import com.modding.forge.items.ItemAccessory;
import com.modding.forge.items.interfaces.IAccessory.EnumAccessoryType;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class InitItems
{
	public static final List<Item> REGISTER_ITEMS = new ArrayList<Item>();
	public static final List<Item> REGISTER_ACCESSORY = new ArrayList<Item>();
	
	public static final Item BRONZE_INGOT = createItem("bronze_ingot");
	public static final Item STEEL_INGOT = createItem("steel_ingot");
	public static final Item TITANIUM_INGOT = createItem("titanium_ingot");
	public static final Item ADAMANTIUM_INGOT = createItem("adamantium_ingot");
	public static final Item TUNGSTEN_INGOT = createItem("tungsten_ingot");
	public static final Item MITHRIL_INGOT = createItem("mithril_ingot");
	public static final Item ORICHALCUM_INGOT = createItem("orichalcum_ingot");
	
	public static final ItemAccessory IRON_RING = createAccessory("iron_ring", EnumAccessoryType.RING);
	public static final ItemAccessory IRON_NECKLACE = createAccessory("iron_necklace", EnumAccessoryType.NECKLACE);
	public static final ItemAccessory DIAMOND_RING = createAccessory("diamond_ring", EnumAccessoryType.RING);
	public static final ItemAccessory DIAMOND_NECKLACE = createAccessory("diamond_necklace", EnumAccessoryType.NECKLACE);
	public static final ItemAccessory EMERALD_RING = createAccessory("emerald_ring", EnumAccessoryType.RING);
	public static final ItemAccessory EMERALD_NECKLACE = createAccessory("emerald_necklace", EnumAccessoryType.NECKLACE);
	
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
	
	private static ItemAccessory createAccessory(String name, EnumAccessoryType type)
	{
		ItemAccessory item = new ItemAccessory(type);
		item.setUnlocalizedName(name);
		item.setRegistryName(Reference.location(name));
		item.setCreativeTab(CreativeTabs.COMBAT);
		item.setMaxStackSize(1);
		
		REGISTER_ACCESSORY.add(item);
		return item;
	}
}
