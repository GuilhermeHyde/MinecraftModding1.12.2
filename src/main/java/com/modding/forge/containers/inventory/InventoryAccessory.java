package com.modding.forge.containers.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

public class InventoryAccessory implements IInventoryAccessory
{
	public NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);
	
	@Override
	public int getSizeInventory()
	{
		return this.inventory.size();
	}

	@Override
	public boolean isEmpty()
	{
		for(ItemStack stack : this.inventory)
		{
			if(!stack.isEmpty()) return false;
		}
		return true;
	}
	
	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.inventory.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		if(this.inventory.get(index) != ItemStack.EMPTY)
		{
			ItemStack stack = ItemStack.EMPTY;
			if(this.inventory.get(index).getCount() <= count)
			{
				stack = this.inventory.get(index);
				this.inventory.set(index, ItemStack.EMPTY);
				this.markDirty();
				return stack;
			}
			else
			{
				stack = this.inventory.get(index).splitStack(count);
				if(this.inventory.get(index).getCount() == 0) this.inventory.set(index, ItemStack.EMPTY);
				this.markDirty();
				return stack;
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		if(this.inventory.get(index) != ItemStack.EMPTY)
		{
			ItemStack stack = this.inventory.get(index);
			this.inventory.set(index, ItemStack.EMPTY);
			return stack;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		if(index < this.inventory.size())
		{
			this.inventory.set(index, stack);
			this.markDirty();
		}
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public void markDirty()
	{
		
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return !player.isDead && player.getDistance(player) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
		
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return true;
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{
		
	}

	@Override
	public int getFieldCount()
	{
		int count = 0;
		for(int slot = 0; slot < this.inventory.size(); ++slot)
		{
			if(this.inventory.get(slot) != ItemStack.EMPTY) ++count;
		}
		return count;
	}

	@Override
	public void clear()
	{
		ItemStack stack;
		for(int slot = 0; slot < this.inventory.size(); ++slot)
		{
			stack = this.inventory.get(slot);
			if(stack != ItemStack.EMPTY) this.inventory.set(slot, ItemStack.EMPTY);
		}
	}

	@Override
	public String getName()
	{
		return "accessory";
	}

	@Override
	public boolean hasCustomName()
	{
		return true;
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return null;
	}
}
