package com.modding.forge.items.interfaces;

public interface IAccessory
{
	EnumAccessoryType getAccessoryType();
	void setAccessoryType(EnumAccessoryType type);
	
	public enum EnumAccessoryType
	{
		NECKLACE, RING;
	}
}
