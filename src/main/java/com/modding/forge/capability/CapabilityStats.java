package com.modding.forge.capability;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.modding.forge.capability.interfaces.ICapabilityMod;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class CapabilityStats implements ICapabilityMod<NBTTagCompound>
{
	private float attackDamage = 0, criticalDamage = 0, moveSpeed = 0, attackSpeed = 0, armorDefense = 0, armorToughness = 0;
	private Map<String, Map<String, Float>> listBuffer = new HashMap<>();
	
	public Map<String, Float> getBuffer(String name)
	{
		return this.listBuffer.get(name);
	}
	
	public boolean isContain(String name)
	{
		return this.listBuffer.containsKey(name);
	}
	
	public int getSize()
	{
		return this.listBuffer.size();
	}
	
	public void applyBuffer(String name, Map<String, Float> map)
	{
		for(Entry<String, Float> entry : map.entrySet()) this.setValue(entry.getKey(), (float)this.getValue(entry.getKey()) + entry.getValue());

		this.listBuffer.merge(name, map, (var1, var2) ->
		{
			var2.forEach((k, v) -> var1.merge(k, v, Float::sum));	
			return var1;
		});
	}
	
	public void removeBuffer(String name)
	{
		for(Entry<String, Float> entry : this.listBuffer.get(name).entrySet())
		{
			this.setValue(entry.getKey(), (float)this.getValue(entry.getKey()) - entry.getValue());
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
	public Object getValue(String name)
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
	
	public Set<Entry<String, Map<String, Float>>> getBuffer()
	{
		return this.listBuffer.entrySet();
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
		tag.setTag("Buffer", this.saveMapToNBT(this.listBuffer));
		return tag;
	}
	
	@Override
	public void deserializeNBT(NBTTagCompound tag)
	{
		this.setValue("AttackDamage", tag.getFloat("AttackDamage"));
		this.setValue("CriticalDamage", tag.getFloat("CriticalDamage"));
		this.setValue("MoveSpeed", tag.getFloat("MoveSpeed"));
		this.setValue("AttackSpeed", tag.getFloat("AttackSpeed"));
		this.setValue("ArmorDefense", tag.getFloat("ArmorDefense"));
		this.setValue("ArmorToughness", tag.getFloat("ArmorToughness"));
		if(tag.hasKey("Buffer", 10)) this.listBuffer = this.loadMapFromNBT(tag.getCompoundTag("Buffer"));
	}
	
	private NBTTagCompound saveMapToNBT(Map<String, Map<String, Float>> map)
	{
		NBTTagCompound tag = new NBTTagCompound();
		for(Entry<String, Map<String, Float>> entry : map.entrySet())
		{
			NBTTagCompound tag1 = new NBTTagCompound();
			Map<String, Float> subMap = entry.getValue();
			for(Entry<String, Float> entry1 : subMap.entrySet()) tag1.setFloat(entry1.getKey(), entry1.getValue());
			tag.setTag(entry.getKey(), tag1);
		}
		return tag;
	}
	
	private Map<String, Map<String, Float>> loadMapFromNBT(NBTTagCompound nbt)
	{
		Map<String, Map<String, Float>> map = new HashMap<>();
		for(String key : nbt.getKeySet())
		{
			NBTTagCompound tag = nbt.getCompoundTag(key);
			Map<String, Float> subMap = new HashMap<>();
			for(String entry : tag.getKeySet()) subMap.put(entry, tag.getFloat(entry));
			map.put(key, subMap);
		}
		return map;
	}
}
