package com.modding.forge.capability;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.modding.forge.capability.interfaces.ICapabilityMod;

public class CapabilityStats implements ICapabilityMod
{
	private float attackDamage, criticalDamage, moveSpeed, attackSpeed, armorDefense, armorToughness;
	private Map<String, Map<String, Float>> listBuffer = new HashMap<>();
	
	public Map<String, Float> getBuffer(String name)
	{
		return this.listBuffer.get(name);
	}
	
	public boolean isContain(String name)
	{
		return this.listBuffer.containsKey(name);
	}
	
	public void applyBuffer(String name, Map<String, Float> map)
	{
		for(Entry<String, Float> entry : map.entrySet())
		{
			this.setValue(entry.getKey(), this.getValue(entry.getKey()) + entry.getValue());
		}
		this.listBuffer.put(name, map);
	}
	
	public void removeBuffer(String name)
	{
		for(Entry<String, Float> entry : this.listBuffer.get(name).entrySet())
		{
			this.setValue(entry.getKey(), this.getValue(entry.getKey()) - entry.getValue());
		}
		this.listBuffer.remove(name);
	}
	
	@Override
	public void setValue(String name, float value)
	{
		switch(name)
		{
		case "AttackDamage":
			this.attackDamage = value;
			break;
		case "CriticalDamage":
			this.criticalDamage = value;
			break;
		case "MoveSpeed":
			this.moveSpeed = value;
			break;
		case "AttackSpeed":
			this.attackSpeed = value;
			break;
		case "ArmorDefense":
			this.armorDefense = value;
			break;
		case "ArmorToughness":
			this.armorToughness = value;
			break;
			default:
				return;
		}
	}

	@Override
	public float getValue(String name)
	{
		switch(name)
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
			return this.armorToughness;
			default:
				return 0.0F;
		}
	}
}
