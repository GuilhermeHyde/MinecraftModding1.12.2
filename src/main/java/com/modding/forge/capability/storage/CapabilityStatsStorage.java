package com.modding.forge.capability.storage;

import com.modding.forge.capability.CapabilityStats;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CapabilityStatsStorage implements IStorage<CapabilityStats>
{
	@Override
	public NBTBase writeNBT(Capability<CapabilityStats> capability, CapabilityStats instance, EnumFacing side)
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setFloat("AttackDamage", instance.getValue("AttackDamage"));
		tag.setFloat("CriticalDamage", instance.getValue("CriticalDamage"));
		tag.setFloat("MoveSpeed", instance.getValue("MoveSpeed"));
		tag.setFloat("AttackSpeed", instance.getValue("AttackSpeed"));
		tag.setFloat("ArmorDefense", instance.getValue("ArmorDefense"));
		tag.setFloat("ArmorTougthness", instance.getValue("ArmorToughness"));
		return tag;
	}

	@Override
	public void readNBT(Capability<CapabilityStats> capability, CapabilityStats instance, EnumFacing side, NBTBase nbt)
	{
		NBTTagCompound tag = (NBTTagCompound) nbt;
		instance.setValue("AttackDamage", tag.getFloat("AttackDamage"));
		instance.setValue("CriticalDamage", tag.getFloat("CriticalDamage"));
		instance.setValue("MoveSpeed", tag.getFloat("MoveSpeed"));
		instance.setValue("AttackSpeed", tag.getFloat("AttackSpeed"));
		instance.setValue("ArmorDefense", tag.getFloat("ArmorDefense"));
		instance.setValue("ArmorTougthness", tag.getFloat("ArmorTougthness"));
	}
}
