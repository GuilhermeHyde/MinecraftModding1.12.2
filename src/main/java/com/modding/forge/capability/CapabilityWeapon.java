package com.modding.forge.capability;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.modding.forge.capability.interfaces.ICapabilityMod;

import net.minecraft.util.text.TextFormatting;
import scala.concurrent.forkjoin.ThreadLocalRandom;

public class CapabilityWeapon implements ICapabilityMod
{
	private float attackDamage, criticalDamage, attackSpeed;
	private String[] nameAttribute = {"AttackDamage", "CriticalDamage", "AttackSpeed"};
	private EnumQuality quality;
	private List<Entry<String, Float>> attribute = new ArrayList<>();
	
	public CapabilityWeapon()
	{
		if(this.attribute.isEmpty() && this.quality == null)
		{
			float chance = ThreadLocalRandom.current().nextFloat();
			for(EnumQuality quality : EnumQuality.values()) if(chance <= quality.getChance()) this.setQuality(quality);
			if(this.getQuality() == null) this.setQuality(EnumQuality.COMMON);
			
			int amount = this.getQuality().getAmount();
			for(int i = 0; i < amount; i++)
			{
				int harmful = ThreadLocalRandom.current().nextInt(this.getQuality().getHarmful(), -1);
				int benefit = ThreadLocalRandom.current().nextInt(1, this.getQuality().getLimit());
				int limit;
				do limit = ThreadLocalRandom.current().nextInt(harmful, benefit);
				while(limit == 0);
				
				String attributeName = this.nameAttribute[ThreadLocalRandom.current().nextInt(this.nameAttribute.length)];
				this.incrementAttritube(attributeName, limit);
			}
		}
	}
	
	public boolean isEmpty()
	{
		return this.attribute.isEmpty();
	}
	
	public int getSize()
	{
		return this.attribute.size();
	}
	
	public void incrementAttritube(String name, float value)
	{
		this.attribute.add(new AbstractMap.SimpleEntry<>(name, value));
		this.attribute.forEach(entry ->
		{
			this.setValue(entry.getKey(), entry.getValue());
		});
	}
	
	public String getAttributeName(int index)
	{
		return this.attribute.get(index).getKey();
	}
	
	public int getAttributeValue(int index)
	{
		return this.attribute.get(index).getValue().intValue();
	}
	
	public EnumQuality getQuality()
	{
		return this.quality;
	}
	
	public void setQuality(EnumQuality quality)
	{
		this.quality = quality;
	}
	
	public TextFormatting getColorValue(float value)
	{
		if(value < 0) return TextFormatting.RED;
		else return TextFormatting.BLUE;
	}
	
	public TextFormatting getColorText(EnumQuality quality)
	{
		switch(quality)
		{
		case COMMON:
			return TextFormatting.GRAY;
		case EPIC:
			return TextFormatting.YELLOW;
		case LEGENDARY:
			return TextFormatting.DARK_PURPLE;
		case RARE:
			return TextFormatting.BLUE;
		default:
			return null;
		}
	}
	
	@Override
	public void setValue(String id, float value)
	{
		switch(id)
		{
		case "AttackDamage":
			this.attackDamage = value;
			break;
		case "CriticalDamage":
			this.criticalDamage = value;
			break;
		case "AttackSpeed":
			this.attackSpeed = value;
			break;
			default:
				return;
		}
	}

	@Override
	public float getValue(String id)
	{
		switch(id)
		{
		case "AttackDamage":
			return this.attackDamage;
		case "CriticalDamage":
			return this.criticalDamage;
		case "AttackSpeed":
			return this.attackSpeed;
			default:
				return 0;
		}
	}
}
