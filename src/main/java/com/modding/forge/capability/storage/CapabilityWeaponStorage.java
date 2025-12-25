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
		NBTTagCompound tag = new NBTTagCompound();
		tag.setFloat("AttackDamage", instance.getValue("AttackDamage"));
		tag.setFloat("CriticalDamage", instance.getValue("CriticalDamage"));
		tag.setFloat("AttackSpeed", instance.getValue("AttackSpeed"));
		return tag;
	}

	@Override
	public void readNBT(Capability<CapabilityWeapon> capability, CapabilityWeapon instance, EnumFacing side, NBTBase nbt)
	{
		NBTTagCompound tag = (NBTTagCompound)nbt;
		instance.setValue("AttackDamage", tag.getFloat("AttackDamage"));
		instance.setValue("CriticalDamage", tag.getFloat("CriticalDamage"));
		instance.setValue("AttackSpeed", tag.getFloat("AttackSpeed"));
	}
}
