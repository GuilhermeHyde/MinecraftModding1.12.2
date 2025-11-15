package com.modding.forge.capability.storage;

import com.modding.forge.capability.CapabilityLevel;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CapabilityLevelStorage implements IStorage<CapabilityLevel>
{
	@Override
	public NBTBase writeNBT(Capability<CapabilityLevel> capability, CapabilityLevel instance, EnumFacing side)
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setFloat("Level", instance.getValue(0));
		tag.setFloat("Points", instance.getValue(1));
		tag.setFloat("Exp", instance.getValue(2));
		tag.setFloat("MaxExp", instance.getValue(3));
		tag.setFloat("Strength", instance.getValue(4));
		tag.setFloat("Resistance", instance.getValue(5));
		tag.setFloat("Agility", instance.getValue(6));
		return tag;
	}

	@Override
	public void readNBT(Capability<CapabilityLevel> capability, CapabilityLevel instance, EnumFacing side, NBTBase nbt)
	{
		NBTTagCompound tag = (NBTTagCompound) nbt;
		instance.setValue(0, tag.getFloat("Level"));
		instance.setValue(1, tag.getFloat("Points"));
		instance.setValue(2, tag.getFloat("Exp"));
		instance.setValue(2, tag.getFloat("MaxExp"));
		instance.setValue(4, tag.getFloat("Strength"));
		instance.setValue(5, tag.getFloat("Resistance"));
		instance.setValue(6, tag.getFloat("Agility"));
	}
}
