package com.modding.forge.capability;

import net.minecraft.util.text.TextFormatting;

public enum EnumQuality
{
	NULL(0, 0, 0, 0.0F),
	COMMON(4, -12, 1, 0.77F),
	RARE(8, -6, 2, 0.55F),
	EPIC(12, -4, 3, 0.08F),
	LEGENDARY(16, -2, 4, 0.02F);
	
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
	
	public static TextFormatting getColorValue(float value)
	{
		if(value < 0) return TextFormatting.RED;
		else return TextFormatting.BLUE;
	}
	
	public static TextFormatting getColorQuality(EnumQuality quality)
	{
		switch(quality)
		{
		case COMMON:
			return TextFormatting.GRAY;
		case RARE:
			return TextFormatting.BLUE;
		case EPIC:
			return TextFormatting.YELLOW;
		case LEGENDARY:
			return TextFormatting.LIGHT_PURPLE;
			default:
				return null;
		}
	}
}
