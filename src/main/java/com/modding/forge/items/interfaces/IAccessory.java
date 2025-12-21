package com.modding.forge.items.interfaces;

public interface IAccessory
{
	EnumAccessoryType getAccessoryType();
	void setAccessoryType(EnumAccessoryType type);
	
	public enum EnumAccessoryType
	{
		NECKLACE("necklace_slot", 1),
		RING("ring_slot", 2);
		
		private String name;
		private int index;
		
		EnumAccessoryType(String name, int index)
		{
			this.name = name;
			this.index = index;
		}
		
		public String getName()
		{
			return this.name;
		}
		
		public int getIndex()
		{
			return this.index;
		}
	}
}
