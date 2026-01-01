package com.modding.forge.capability;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.modding.forge.capability.interfaces.ICapabilityMod;
import com.modding.forge.capability.provider.CapabilityWeaponProvider;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import scala.concurrent.forkjoin.ThreadLocalRandom;

public class CapabilityWeapon implements ICapabilityMod<NBTTagCompound>
{
	private float attackDamage, criticalDamage, attackSpeed;
	private String[] attributeName = {"AttackDamage", "CriticalDamage", "AttackSpeed"};
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
		Map<String, Float> attributeMap = new HashMap<>();
		if(!this.attribute.isEmpty())
		{
			for(Entry<String, Float> entry : this.attribute)
			{
				attributeMap.merge(entry.getKey(), entry.getValue(), Float::sum);
			}
		}
		return attributeMap;
	}
	
	public String getAttributeName(int index)
	{
		return this.attribute.get(index).getKey();
	}
	
	public int getAttributeValue(int index)
	{
		return this.attribute.get(index).getValue().intValue();
	}
	
	public void randomAttribute(ItemStack stack)
	{
		CapabilityWeapon cap = stack.getCapability(CapabilityWeaponProvider.WEAPON_ATTRIBUTE_CAP, null);
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
	
	public EnumQuality getQuality()
	{
		return this.quality;
	}
	
	public void setQuality(EnumQuality quality)
	{
		this.quality = quality;
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
	public Object getValue(String id)
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
	
	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setFloat("AttackDamage", (float)this.getValue("AttackDamage"));
		tag.setFloat("CriticalDamage", (float)this.getValue("CriticalDamage"));
		tag.setFloat("AttackSpeed", (float)this.getValue("AttackSpeed"));
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
	public void deserializeNBT(NBTTagCompound tag)
	{
		this.attribute.clear();
		this.setValue("AttackDamage", tag.getFloat("AttackDamage"));
		this.setValue("CriticalDamage", tag.getFloat("CriticalDamage"));
		this.setValue("AttackSpeed", tag.getFloat("AttackSpeed"));
		this.setQuality(EnumQuality.valueOf(tag.getString("Quality")));
		
		NBTTagList tagList = tag.getTagList("Attributes", 10);
		for(int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound value = tagList.getCompoundTagAt(i);
			this.incrementAttritube(value.getString("K"), value.getFloat("V"));
		}
	}
}
