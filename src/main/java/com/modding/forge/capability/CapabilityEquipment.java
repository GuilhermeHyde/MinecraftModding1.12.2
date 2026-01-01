package com.modding.forge.capability;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.modding.forge.capability.interfaces.ICapabilityMod;
import com.modding.forge.capability.provider.CapabilityEquipmentProvider;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import scala.concurrent.forkjoin.ThreadLocalRandom;

public class CapabilityEquipment implements ICapabilityMod<NBTTagCompound>
{
	private float armorDefense, armorToughness, moveSpeed;
	private String[] attributeName = {"ArmorDefense", "ArmorToughness", "MoveSpeed"};
	private EnumQuality quality = EnumQuality.NULL;
	private List<Entry<String, Float>> attribute = new ArrayList<>();
	
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
	
	public void randomAttribute(ItemStack stack)
	{
		CapabilityEquipment cap = stack.getCapability(CapabilityEquipmentProvider.EQUIPMENT_ATTRIBUTE_CAP, null);
		if(cap != null)
		{
			float chance;
			do
			{
				chance = ThreadLocalRandom.current().nextFloat();
				for(EnumQuality quality : EnumQuality.values()) if(chance <= quality.getChance()) cap.setQuality(quality);
			}
			while(cap.getQuality() == EnumQuality.NULL);
			
			for(int i = 0; i < cap.getQuality().getAmount(); i++)
			{
				int value;
				do
				{
					value = ThreadLocalRandom.current().nextInt(cap.getQuality().getHarmful(), cap.getQuality().getLimit());
				}
				while(value == 0);
				
				String name = this.attributeName[ThreadLocalRandom.current().nextInt(this.attributeName.length)];
				cap.incrementAttritube(name, value);
			}
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
			tag.setTag("Attributes", tagList);
		}
		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		this.setValue("ArmorDefense", nbt.getFloat("ArmorDefense"));
		this.setValue("ArmorTougness", nbt.getFloat("ArmorTougness"));
		this.setValue("MoveSpeed", nbt.getFloat("MoveSpeed"));
		this.setQuality(EnumQuality.valueOf(nbt.getString("Quality")));
		
		NBTTagList tagList = nbt.getTagList("Attributes", 10);
		for(int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound value = tagList.getCompoundTagAt(i);
			this.incrementAttritube(value.getString("K"), value.getFloat("V"));
		}
	}
}
