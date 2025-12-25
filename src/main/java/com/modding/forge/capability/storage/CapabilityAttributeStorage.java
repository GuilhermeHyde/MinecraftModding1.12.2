 package com.modding.forge.capability.storage;

import com.modding.forge.capability.CapabilityAttribute;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CapabilityAttributeStorage implements IStorage<CapabilityAttribute>
{
	@Override
	public NBTBase writeNBT(Capability<CapabilityAttribute> capability, CapabilityAttribute instance, EnumFacing side) 
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setFloat("AttackDamage", instance.getValue("AttackDamage"));
		tag.setFloat("CriticalDamage", instance.getValue("CriticalDamage"));
		tag.setFloat("MoveSpeed", instance.getValue("MoveSpeed"));
		tag.setFloat("AttackSpeed", instance.getValue("AttackSpeed"));
		tag.setFloat("ArmorDefense", instance.getValue("ArmorDefense"));
		tag.setFloat("ArmorToughtness", instance.getValue("ArmorToughtness"));
		return tag;
	}

	@Override
	public void readNBT(Capability<CapabilityAttribute> capability, CapabilityAttribute instance, EnumFacing side, NBTBase nbt)
	{
		NBTTagCompound tag = (NBTTagCompound)nbt;
		instance.setValue("AttackDamage", tag.getFloat("AttackDamage"));
		instance.setValue("CriticalDamage", tag.getFloat("CriticalDamage"));
		instance.setValue("MoveSpeed", tag.getFloat("MoveSpeed"));
		instance.setValue("AttackSpeed", tag.getFloat("AttackSpeed"));
		instance.setValue("ArmorDefense", tag.getFloat("ArmorDefense"));
		instance.setValue("ArmorToughness", tag.getFloat("ArmorToughness"));
	}
}
