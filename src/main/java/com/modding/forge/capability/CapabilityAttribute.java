package com.modding.forge.capability;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.modding.forge.capability.interfaces.ICapabilityMod;

public class CapabilityAttribute implements ICapabilityMod
{
	private float attackDamage, criticalDamage, moveSpeed, attackSpeed, armorDefense, armorToughtness;
	private EnumQuality quality = EnumQuality.NULL;
	private Map<String, Float> listAttribute = new HashMap<>();
	
	public CapabilityAttribute()
	{
		if(this.listAttribute.isEmpty() && this.getQuality() == EnumQuality.NULL)
		{
			this.setQuality(1);
			int limit = this.getQuality().getLimit();
			int amount = this.getQuality().getAmount();
			
			for(int i = 0; i < amount; i++)
			{
				String attribute = this.getString(i);
				this.incrementAttritube(attribute, limit);
			}
		}
	}
	
	public Set<Entry<String, Float>> getMap()
	{
		return this.listAttribute.entrySet();
	}
	
	public int getSize()
	{
		return this.listAttribute.size();
	}
	
	public boolean isEmpty()
	{
		return this.listAttribute.isEmpty();
	}
	
	public EnumQuality getQuality()
	{
		return this.quality;
	}
	
	public void incrementAttritube(String name, float value)
	{
		this.listAttribute.put(name, value);
		for(Entry<String, Float> entry : this.listAttribute.entrySet())
		{
			this.setValue(entry.getKey(), entry.getValue());
		}
	}
	
	public void removeAttribute(String name)
	{
		for(Entry<String, Float> entry : this.listAttribute.entrySet())
		{
			this.setValue(entry.getKey(), 0);
		}
		this.listAttribute.remove(name);
	}
	
	public void setQuality(int index)
	{
		switch(index)
		{
		case 0:
			this.quality = EnumQuality.COMMON;
			break;
		case 1:
			this.quality = EnumQuality.RARE;
			break;
		case 2:
			this.quality = EnumQuality.EPIC;
			break;
		case 3:
			this.quality = EnumQuality.LEGENDARY;
			break;
			default:
				return;
		}
	}
	
	public String getString(int index)
	{
		switch(index)
		{
		case 0:
			return "AttackDamage";
		case 1:
			return "CriticalDamage";
		case 2:
			return "MoveSpeed";
		case 3:
			return "AttackSpeed";
		case 4:
			return "ArmorDefense";
		case 5:
			return "ArmorToughness";
			default:
				return "null";
		}
	}
	
	@Override
	public void setValue(String id, float value)
	{
		switch(id)
		{
		case "AttackDamage":
			this.attackDamage = value;
		case "CriticalDamage":
			this.criticalDamage = value;
		case "MoveSpeed":
			this.moveSpeed = value;
		case "AttackSpeed":
			this.attackSpeed = value;
		case "ArmorDefense":
			this.armorDefense = value;
		case "ArmorToughness":
			this.armorToughtness = value;
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
		case "MoveSpeed":
			return this.moveSpeed;
		case "AttackSpeed":
			return this.attackSpeed;
		case "ArmorDefense":
			return this.armorDefense;
		case "ArmorToughness":
			return this.armorToughtness;
			default:
				return 0;
		}
	}
}
