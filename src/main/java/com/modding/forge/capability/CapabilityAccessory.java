package com.modding.forge.capability;

import java.util.ArrayList;
import java.util.List;

import com.modding.forge.capability.interfaces.ICapabilityAccessory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class CapabilityAccessory implements ICapabilityAccessory
{
	public final ItemStackHandler inventory = new ItemStackHandler(3);
	private List<ItemStack> listItem = new ArrayList<>();
	
	@Override
	public int getSlots()
	{
		return this.inventory.getSlots();
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return this.inventory.getStackInSlot(slot);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
	{
		return this.inventory.insertItem(slot, stack, simulate);
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate)
	{
		return this.inventory.extractItem(slot, amount, simulate);
	}

	@Override
	public int getSlotLimit(int slot)
	{
		return this.inventory.getSlotLimit(slot);
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack)
	{
		this.inventory.setStackInSlot(slot, stack);
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		return this.inventory.serializeNBT();
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		this.inventory.deserializeNBT(nbt);
	}
	
	public boolean compareItemStack(ItemStack item, int index)
	{
		if(this.listItem.isEmpty()) while(this.listItem.size() < this.getSlots()) this.listItem.add(index, ItemStack.EMPTY);
		
		if(!ItemStack.areItemsEqual(this.listItem.get(index), item) && !ItemStack.areItemsEqual(this.listItem.get(index), ItemStack.EMPTY))
		{
			this.listItem.set(index, item);
			return true;
		}
		else
		{
			if(ItemStack.areItemsEqual(this.listItem.get(index), ItemStack.EMPTY)) this.listItem.set(index, item);
			return false;
		}
	}
}
