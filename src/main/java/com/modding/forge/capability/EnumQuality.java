package com.modding.forge.capability;

public enum EnumQuality
{
	NULL(0, 0),
	COMMON(4, 1),
	RARE(8, 2),
	EPIC(12, 3),
	LEGENDARY(16, 4);
	
	private int limit, amount;
	
	EnumQuality(int limit, int amount)
	{
		this.limit = limit;
		this.amount = amount;
	}
	
	public int getLimit()
	{
		return this.limit;
	}
	
	public int getAmount()
	{
		return this.amount;
	}
}
