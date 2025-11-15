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
		tag.setFloat("AttackDamage", instance.getValue(0));
		tag.setFloat("CriticalDamage", instance.getValue(1));
		tag.setFloat("MoveSpeed", instance.getValue(2));
		tag.setFloat("AttackSpeed", instance.getValue(3));
		tag.setFloat("ArmorDefense", instance.getValue(4));
		tag.setFloat("ArmorTougthness", instance.getValue(5));
		return tag;
	}

	@Override
	public void readNBT(Capability<CapabilityStats> capability, CapabilityStats instance, EnumFacing side, NBTBase nbt)
	{
		NBTTagCompound tag = (NBTTagCompound) nbt;
		instance.setValue(0, tag.getFloat("AttackDamage"));
		instance.setValue(1, tag.getFloat("CriticalDamage"));
		instance.setValue(2, tag.getFloat("MoveSpeed"));
		instance.setValue(3, tag.getFloat("AttackSpeed"));
		instance.setValue(4, tag.getFloat("ArmorDefense"));
		instance.setValue(5, tag.getFloat("ArmorTougthness"));
	}
}
