package com.modding.forge.capability.storage;

import com.modding.forge.capability.CapabilityWeapon;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CapabilityWeaponStorage implements IStorage<CapabilityWeapon>
{
	@Override
	public NBTBase writeNBT(Capability<CapabilityWeapon> capability, CapabilityWeapon instance, EnumFacing side)
	{
		return instance.serializeNBT();
	}

	@Override
	public void readNBT(Capability<CapabilityWeapon> capability, CapabilityWeapon instance, EnumFacing side, NBTBase nbt)
	{
		if(nbt instanceof NBTTagCompound) instance.deserializeNBT((NBTTagCompound)nbt);
	}
}
