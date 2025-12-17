package com.modding.forge.items.interfaces;

import com.modding.forge.items.ItemAccessory;

public interface IAccessory
{
	EnumAccessoryType getAccessoryType();
	void setAccessoryType(EnumAccessoryType type);
	
	public enum EnumAccessoryType
	{
		NECKLACE, RING;
	}
}
