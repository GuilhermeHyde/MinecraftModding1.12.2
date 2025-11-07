package com.modding.forge.items;

import net.minecraft.item.Item;

public class ItemAccessory extends Item implements IAccessory
{
	private EnumAccessoryType accessoryType;
	
	public ItemAccessory(EnumAccessoryType type)
	{
		this.setAccessoryType(type);
	}
	
	@Override
	public EnumAccessoryType getAccessoryType()
	{
		return this.accessoryType;
	}

	@Override
	public void setAccessoryType(EnumAccessoryType type)
	{
		this.accessoryType = type;
	}
}
