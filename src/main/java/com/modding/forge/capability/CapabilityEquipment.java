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

public class CapabilityEquipment implements ICapabilityMod<NBTTagCompound>
{
	private float armorDefense, armorToughness, moveSpeed;
	private String[] attributeName = {"ArmorDefense", "ArmorToughness", "MoveSpeed"};
	private EnumQuality quality = EnumQuality.NULL;
	private List<Entry<String, Float>> attribute = new ArrayList<>();
	
	public void randomAttribute()
	{
		if(this.isEmpty()) this.attribute.clear();
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
	
	public boolean isEmpty()
	{
		return this.attribute.isEmpty();
	}
	
	public int getSize()
	{
		return this.attribute.size();
	}
	
	public void incrementAttribute(String name, float value)
	{
		this.attribute.add(new AbstractMap.SimpleEntry<>(name, value));
		this.attribute.forEach(entry ->
		{
			this.setValue(entry.getKey(), entry.getValue());
		});
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
	public void setValue(String id, Object value)
	{
		switch(id)
		{
		case "ArmorDefense":
			this.armorDefense = (float)value;
		case "ArmorToughness":
			this.armorToughness = (float)value;
		case "MoveSpeed":
			this.moveSpeed = (float)value;
			default:
				return;
		}
	}

	@Override
	public Object getValue(String id)
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

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setFloat("ArmorDefense", (float)this.getValue("ArmorDefense"));
		tag.setFloat("ArmorToughness", (float)this.getValue("ArmorToughness"));
		tag.setFloat("MoveSpeed", (float)this.getValue("MoveSpeed"));
		tag.setString("Quality", this.getQuality().name());
		NBTTagList tagList = new NBTTagList();
		for(Entry<String, Float> entry : this.attribute)
		{
			NBTTagCompound tag1 = new NBTTagCompound();
			tag1.setString("K", entry.getKey());
			tag1.setFloat("V", entry.getValue());
			tagList.appendTag(tag1);
		}
		tag.setTag("Attributes", tagList);
		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		this.attribute.clear();
		this.setValue("ArmorDefense", nbt.getFloat("ArmorDefense"));
		this.setValue("ArmorTougness", nbt.getFloat("ArmorTougness"));
		this.setValue("MoveSpeed", nbt.getFloat("MoveSpeed"));
		this.setQuality(EnumQuality.valueOf(nbt.getString("Quality")));
		
		NBTTagList tagList = nbt.getTagList("Attributes", 10);
		for(int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound value = tagList.getCompoundTagAt(i);
			this.incrementAttribute(value.getString("K"), value.getFloat("V"));
		}
	}
}
