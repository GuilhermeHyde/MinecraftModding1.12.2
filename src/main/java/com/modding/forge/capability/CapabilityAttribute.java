package com.modding.forge.capability;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.modding.forge.capability.interfaces.ICapabilityMod;
import com.modding.forge.capability.provider.CapabilityAttributeProvider;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import scala.concurrent.forkjoin.ThreadLocalRandom;

public class CapabilityAttribute implements ICapabilityMod<NBTTagCompound>
{
	private float attackDamage, criticalDamage, moveSpeed, attackSpeed, armorDefense, armorToughness;
	private String[] attributeName = {"AttackDamage", "CriticalDamage", "MoveSpeed", "AttackSpeed", "ArmorDefense", "ArmorToughness"};
	private EnumQuality quality = EnumQuality.NULL;
	private List<Entry<String, Float>> attribute = new ArrayList<>();
	
	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setFloat("AttackDamage", this.getValue("AttackDamage"));
		tag.setFloat("CriticalDamage", this.getValue("CriticalDamage"));
		tag.setFloat("MoveSpeed", this.getValue("MoveSpeed"));
		tag.setFloat("AttackSpeed", this.getValue("AttackSpeed"));
		tag.setFloat("ArmorDefense", this.getValue("ArmorDefense"));
		tag.setFloat("ArmorToughness", this.getValue("ArmorToughness"));
		
		NBTTagList tagList = new NBTTagList();
		for(Entry<String, Float> entry : this.attribute)
		{
			NBTTagCompound tag1 = new NBTTagCompound();
			tag1.setString("K", entry.getKey());
			tag1.setFloat("K", entry.getValue());
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
		
		NBTTagList tagList = nbt.getTagList("Attributes", 10);
		for(int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound tag = tagList.getCompoundTagAt(i);
			this.incrementAttribute(tag.getString("K"), tag.getFloat("V"));
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
	
	public void randomAttribute(ItemStack stack)
	{
		CapabilityAttribute cap = stack.getCapability(CapabilityAttributeProvider.ACCESSORY_ATTRIBUTES_CAP, null);
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
				cap.incrementAttribute(name, value);
			}
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
			return this.armorToughness;
			default:
				return 0;
		}
	}
}
