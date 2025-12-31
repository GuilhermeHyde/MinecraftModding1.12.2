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
		return instance.serializeNBT();
	}

	@Override
	public void readNBT(Capability<CapabilityAttribute> capability, CapabilityAttribute instance, EnumFacing side, NBTBase nbt)
	{
		if(nbt instanceof NBTTagCompound)instance.deserializeNBT((NBTTagCompound)nbt);
	}
}
