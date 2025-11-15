package com.modding.forge.capability.interfaces;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public interface ICapabilityAccessory extends IItemHandler, IItemHandlerModifiable, INBTSerializable<NBTTagCompound>
{

}
