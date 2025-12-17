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
		tag.setFloat("Level", instance.getValue("Level"));
		tag.setFloat("Points", instance.getValue("Points"));
		tag.setFloat("Exp", instance.getValue("Exp"));
		tag.setFloat("MaxExp", instance.getValue("MaxExp"));
		tag.setFloat("Strength", instance.getValue("Strength"));
		tag.setFloat("Resistance", instance.getValue("Resistance"));
		tag.setFloat("Agility", instance.getValue("Agility"));
		return tag;
	}

	@Override
	public void readNBT(Capability<CapabilityLevel> capability, CapabilityLevel instance, EnumFacing side, NBTBase nbt)
	{
		NBTTagCompound tag = (NBTTagCompound) nbt;
		instance.setValue("Level", tag.getFloat("Level"));
		instance.setValue("Points", tag.getFloat("Points"));
		instance.setValue("Exp", tag.getFloat("Exp"));
		instance.setValue("MaxExp", tag.getFloat("MaxExp"));
		instance.setValue("Strength", tag.getFloat("Strength"));
		instance.setValue("Resistance", tag.getFloat("Resistance"));
		instance.setValue("Agility", tag.getFloat("Agility"));
	}
}
