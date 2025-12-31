package com.modding.forge.capability.storage;

import com.modding.forge.capability.CapabilityEquipment;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CapabilityEquipmentStorage implements IStorage<CapabilityEquipment>
{
	@Override
	public NBTBase writeNBT(Capability<CapabilityEquipment> capability, CapabilityEquipment instance, EnumFacing side)
	{
		return instance.serializeNBT();
	}

	@Override
	public void readNBT(Capability<CapabilityEquipment> capability, CapabilityEquipment instance, EnumFacing side, NBTBase nbt)
	{
		if(nbt instanceof NBTTagCompound) instance.deserializeNBT((NBTTagCompound)nbt);
	}
}
