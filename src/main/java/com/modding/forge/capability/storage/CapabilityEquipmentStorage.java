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
		NBTTagCompound tag = new NBTTagCompound();
		tag.setFloat("ArmorDefense", instance.getValue("ArmorDefense"));
		tag.setFloat("ArmorToughness", instance.getValue("ArmorToughness"));
		tag.setFloat("MoveSpeed", instance.getValue("MoveSpeed"));
		return tag;
	}

	@Override
	public void readNBT(Capability<CapabilityEquipment> capability, CapabilityEquipment instance, EnumFacing side, NBTBase nbt)
	{
		NBTTagCompound tag = (NBTTagCompound)nbt;
		instance.setValue("ArmorDefense", tag.getFloat("ArmorDefense"));
		instance.setValue("ArmorToughness", tag.getFloat("ArmorToughness"));
		instance.setValue("MoveSpeed", tag.getFloat("MoveSpeed"));
	}
}
