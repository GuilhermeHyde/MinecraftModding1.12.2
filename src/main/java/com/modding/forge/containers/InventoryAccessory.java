package com.modding.forge.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class InventoryAccessory implements IInventory
{
	private final NonNullList<ItemStack> stackList = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);
	private EntityPlayer player;
	
	public InventoryAccessory(EntityPlayer player)
	{
		this.player = player;
	}
	
	@Override
	public String getName() 
	{
		return "container.accessory";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return (ITextComponent)(this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]));
	}

	@Override
	public int getSizeInventory()
	{
		return this.stackList.size();
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack itemstack : this.stackList)
        {
            if (!itemstack.isEmpty()) return false;
        }
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return index >= this.getSizeInventory() ? ItemStack.EMPTY : (ItemStack)this.stackList.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		if (this.stackList.get(index) != ItemStack.EMPTY)
		{
			ItemStack itemstack = ItemStack.EMPTY;
			if (this.stackList.get(index).getCount() <= count)
			{
				itemstack = this.stackList.get(index);
				this.stackList.set(index, ItemStack.EMPTY);
				this.markDirty();
				return itemstack;
			}
			else
			{
				itemstack = this.stackList.get(index).splitStack(count);

				if (this.stackList.get(index).getCount() == 0) this.stackList.set(index, ItemStack.EMPTY);
				this.markDirty();
				return itemstack;
			}

		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		if (this.stackList.get(index) != ItemStack.EMPTY)
		{
			ItemStack itemstack = this.stackList.get(index);
			this.stackList.set(index, ItemStack.EMPTY);
			return itemstack;
		}

		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		if (index < this.stackList.size())
		{
			this.stackList.set(index, stack);
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
		//ModNetworkingManager.INSTANCE.sendToAll(new CapabilityAccessoryPacket(this.player.getEntityId(), player.serializeNBT()));
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return !player.isDead && player.getDistanceSq(this.player) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return true;
	}

	@Override
	public int getField(int id)
	{
		return id;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount()
	{
		int count = 0;
		for (int slot = 0; slot < this.stackList.size(); ++slot)
		{
			if (this.stackList.get(slot) != ItemStack.EMPTY) ++count;
		}
		return count;
	}

	@Override
	public void clear()
	{
		ItemStack itemstack;
		for (int slot = 0; slot < this.stackList.size(); ++slot)
		{
			itemstack = this.stackList.get(slot);
			if (itemstack != ItemStack.EMPTY) this.stackList.set(slot, ItemStack.EMPTY);
		}
		this.markDirty();
	}

}
