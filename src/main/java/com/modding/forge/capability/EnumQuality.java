package com.modding.forge.capability;

public enum EnumQuality
{
	NULL(0, 0, 0, 0.0F),
	COMMON(4, -12, 1, 0.77F),
	RARE(8, -6, 2, 0.55F),
	EPIC(12, -4, 3, 0.15F),
	LEGENDARY(16, -2, 4, 0.04F);
	
	private int limit, harmful, amount;
	private float chance;
	
	EnumQuality(int limit, int harmful, int amount, float chance)
	{
		this.limit = limit;
		this.harmful = harmful;
		this.amount = amount;
		this.chance = chance;
	}
	
	public int getLimit()
	{
		return this.limit;
	}
	
	public int getHarmful()
	{
		return this.harmful;
	}
	
	public int getAmount()
	{
		return this.amount;
	}
	
	public float getChance()
	{
		return this.chance;
	}
}
