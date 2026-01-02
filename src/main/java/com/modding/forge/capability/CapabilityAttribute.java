package com.modding.forge.capability;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.modding.forge.capability.interfaces.ICapabilityMod;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import scala.concurrent.forkjoin.ThreadLocalRandom;

public class CapabilityAttribute implements ICapabilityMod<NBTTagCompound>
{
	private float attackDamage, criticalDamage, moveSpeed, attackSpeed, armorDefense, armorToughness;
	private String[] attributeName = {"AttackDamage", "CriticalDamage", "MoveSpeed", "AttackSpeed", "ArmorDefense", "ArmorToughness"};
	private EnumQuality quality = EnumQuality.NULL;
	private List<Entry<String, Float>> attribute = new ArrayList<>();
	
	public void randomAttribute()
	{
		if(!this.isEmpty()) this.attribute.clear();
		float chance;
		do
		{
			chance = ThreadLocalRandom.current().nextFloat();
			for(EnumQuality quality : EnumQuality.values()) if(chance <= quality.getChance()) this.setQuality(quality);
		}
		while(this.getQuality() == EnumQuality.NULL);
		
		for(int i = 0; i < this.getQuality().getAmount(); i++)
		{
			int value;
			do
			{
				value = ThreadLocalRandom.current().nextInt(this.getQuality().getHarmful(), this.getQuality().getLimit());
			}
			while(value == 0);
			
			String name = this.attributeName[ThreadLocalRandom.current().nextInt(this.attributeName.length)];
			this.incrementAttribute(name, value);
		}
	}
	
	public void incrementAttribute(String name, float value)
	{
		this.attribute.add(new AbstractMap.SimpleEntry<>(name, value));
		this.attribute.forEach(entry ->
		{
			this.setValue(entry.getKey(), entry.getValue());
		});
	}
	
	public boolean isEmpty()
	{
		return this.attribute.isEmpty();
	}
	
	public int getSize()
	{
		return this.attribute.size();
	}
	
	public Map<String, Float> getAttributes()
	{
		Map<String, Float> map = new HashMap<>();
		if(!this.attribute.isEmpty())
		{
			for(Entry<String, Float> entry : this.attribute)
			{
				map.merge(entry.getKey(), entry.getValue(), Float::sum);
			}
		}
		return map;
	}
	
	public EnumQuality getQuality()
	{
		return this.quality;
	}
	
	public void setQuality(EnumQuality quality)
	{
		this.quality = quality;
	}
	
	public int getAttributeValue(int index)
	{
		return this.attribute.get(index).getValue().intValue();
	}
	
	public String getAttributeName(int index)
	{
		return this.attribute.get(index).getKey();
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
			default:
				return;
		}
	}

	@Override
	public Object getValue(String id)
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
			return this.armorToughness;
			default:
				return 0;
		}
	}
	
	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setFloat("AttackDamage", (float)this.getValue("AttackDamage"));
		tag.setFloat("CriticalDamage", (float)this.getValue("CriticalDamage"));
		tag.setFloat("MoveSpeed", (float)this.getValue("MoveSpeed"));
		tag.setFloat("AttackSpeed", (float)this.getValue("AttackSpeed"));
		tag.setFloat("ArmorDefense", (float)this.getValue("ArmorDefense"));
		tag.setFloat("ArmorToughness", (float)this.getValue("ArmorToughness"));
		tag.setString("Quality", this.getQuality().name());
		
		NBTTagList tagList = new NBTTagList();
		for(Entry<String, Float> entry : this.attribute)
		{
			NBTTagCompound tag1 = new NBTTagCompound();
			tag1.setString("K", entry.getKey());
			tag1.setFloat("V", entry.getValue());
			tagList.appendTag(tag1);
			tag.setTag("Attributes", tagList);
		}
		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		this.attribute.clear();
		this.setValue("AttackDamage", nbt.getFloat("AttackDamage"));
		this.setValue("CriticalDamage", nbt.getFloat("CriticalDamage"));
		this.setValue("MoveSpeed", nbt.getFloat("MoveSpeed"));
		this.setValue("AttackSpeed", nbt.getFloat("AttackSpeed"));
		this.setValue("ArmorDefense", nbt.getFloat("ArmorDefense"));
		this.setValue("ArmorToughness", nbt.getFloat("ArmorToughness"));
		this.setQuality(EnumQuality.valueOf(nbt.getString("Quality")));
		
		NBTTagList tagList = nbt.getTagList("Attributes", 10);
		for(int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound tag = tagList.getCompoundTagAt(i);
			this.incrementAttribute(tag.getString("K"), tag.getFloat("V"));
		}
	}
}
