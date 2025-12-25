package com.modding.forge.capability;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.modding.forge.capability.interfaces.ICapabilityMod;

public class CapabilityEquipment implements ICapabilityMod
{
	private float armorDefense, armorToughness, moveSpeed;
	private EnumQuality quality;
	private Map<String, Float> attribute = new HashMap<>();
	
	public CapabilityEquipment()
	{
		if(this.attribute.isEmpty() && this.quality == null)
		{
			this.setQuality(3);
			int limit = this.getQuality().getLimit();
			int amount = this.getQuality().getAmount();
			
			for(int i = 0; i < amount; i++)
			{
				String attributeName = this.getString(i);
				this.incrementAttritube(attributeName, limit);
			}
		}
	}
	
	public void incrementAttritube(String name, float value)
	{
		this.attribute.put(name, value);
		for(Entry<String, Float> entry : this.attribute.entrySet())
		{
			this.setValue(entry.getKey(), entry.getValue());
		}
	}
	
	public EnumQuality getQuality()
	{
		return this.quality;
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
			return "ArmorDefense";
		case 1:
			return "ArmorToughness";
		case 2:
			return "MoveSpeed";
			default:
				return "null";
		}
	}
	
	@Override
	public void setValue(String id, float value)
	{
		switch(id)
		{
		case "ArmorDefense":
			this.armorDefense = value;
		case "ArmorToughness":
			this.armorToughness = value;
		case "MoveSpeed":
			this.moveSpeed = value;
			default:
				return;
		}
	}

	@Override
	public float getValue(String id)
	{
		switch(id)
		{
		case "ArmorDefense":
			return this.armorDefense;
		case "ArmorToughness":
			return this.armorToughness;
		case "MoveSpeed":
			return this.moveSpeed;
			default:
				return 0;
		}
	}
}
