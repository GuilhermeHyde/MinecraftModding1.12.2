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
		return instance.serializeNBT();
	}

	@Override
	public void readNBT(Capability<CapabilityStats> capability, CapabilityStats instance, EnumFacing side, NBTBase nbt)
	{
		if(nbt instanceof NBTTagCompound) instance.deserializeNBT((NBTTagCompound)nbt);
	}
}
