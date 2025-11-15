package com.modding.forge.capability.storage;

import com.modding.forge.capability.CapabilityAccessory;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CapabilityAccessoryStorage implements IStorage<CapabilityAccessory>
{
	@Override
	public NBTBase writeNBT(Capability<CapabilityAccessory> capability, CapabilityAccessory instance, EnumFacing side)
	{
		return instance.serializeNBT();
	}

	@Override
	public void readNBT(Capability<CapabilityAccessory> capability, CapabilityAccessory instance, EnumFacing side, NBTBase nbt)
	{
		instance.deserializeNBT((NBTTagCompound)nbt);
	}
}
