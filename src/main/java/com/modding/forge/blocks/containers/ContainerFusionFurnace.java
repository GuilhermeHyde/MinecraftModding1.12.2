package com.modding.forge.blocks.containers;

import java.util.Map;
import java.util.Map.Entry;

import com.modding.forge.blocks.recipes.FusionFurnaceRecipe;
import com.modding.forge.blocks.tilentities.TileEntityFusionFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerFusionFurnace extends Container
{
	private final TileEntityFusionFurnace TILE_ENTITY;
	private int heat, maxHeat, castingProcess, meltingProcess, maxCasting, maxMelting;
	
	public ContainerFusionFurnace(InventoryPlayer inventory, TileEntityFusionFurnace tileEntity)
	{
		this.TILE_ENTITY = tileEntity;
		IItemHandler handler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		this.addSlotToContainer(new SlotItemHandler(handler, 0, 30, 25)); //input1
		this.addSlotToContainer(new SlotItemHandler(handler, 1, 71, 25)); //input2
		this.addSlotToContainer(new SlotItemHandler(handler, 2, 17, 53)); //fuel
		this.addSlotToContainer(new SlotItemHandler(handler, 3, 128, 25)); //output
		
		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 9; x++)
			{
				this.addSlotToContainer(new Slot(inventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}
		
		for(int x = 0; x < 9; x++)
		{
			this.addSlotToContainer(new Slot(inventory, x, 8 + x * 18, 142));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return this.TILE_ENTITY.isUsableByPlayer(playerIn);
	}
	
	@Override
	public void updateProgressBar(int id, int data)
	{
		this.TILE_ENTITY.setField(id, data);
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); ++i)
		{
			IContainerListener listener = (IContainerListener)this.listeners.get(i);
			if(this.castingProcess != this.TILE_ENTITY.getField(0)) listener.sendWindowProperty(this, 0, this.TILE_ENTITY.getField(0));
			if(this.meltingProcess != this.TILE_ENTITY.getField(1)) listener.sendWindowProperty(this, 1, this.TILE_ENTITY.getField(1));
			if(this.maxCasting != this.TILE_ENTITY.getField(2)) listener.sendWindowProperty(this, 2, this.TILE_ENTITY.getField(2));
			if(this.maxMelting != this.TILE_ENTITY.getField(3)) listener.sendWindowProperty(this, 3, this.TILE_ENTITY.getField(3));
			if(this.heat != this.TILE_ENTITY.getField(4)) listener.sendWindowProperty(this, 4, this.TILE_ENTITY.getField(4));
			if(this.maxHeat != this.TILE_ENTITY.getField(5)) listener.sendWindowProperty(this, 5, this.TILE_ENTITY.getField(5));
		}
		
		this.castingProcess = this.TILE_ENTITY.getField(0);
		this.meltingProcess = this.TILE_ENTITY.getField(1);
		this.maxCasting = this.TILE_ENTITY.getField(2);
		this.maxMelting = this.TILE_ENTITY.getField(3);
		this.heat = this.TILE_ENTITY.getField(4);
		this.maxHeat = this.TILE_ENTITY.getField(5);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index)
	{
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot)this.inventorySlots.get(index);
		
		if(slot != null && slot.getHasStack())
		{
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			
			if(index == 3)
			{
				if(!this.mergeItemStack(stack1, 4, 40, true)) return ItemStack.EMPTY;
				slot.onSlotChange(stack1, stack);
			}
			else if(index != 2 && index != 0 && index != 1)
			{
				for(Entry<ItemStack, Map<ItemStack, ItemStack>> entry : FusionFurnaceRecipe.getInstance().getMaterialRecipe().columnMap().entrySet())
				{
					for(Entry<ItemStack, ItemStack> entry1 : entry.getValue().entrySet())
					{
						if(FusionFurnaceRecipe.getInstance().compareItemStack(stack1, entry.getKey()))
						{
							if(!this.mergeItemStack(stack1, 0, 2, false)) return ItemStack.EMPTY;
						}
						else if(FusionFurnaceRecipe.getInstance().compareItemStack(stack1, entry1.getKey()))
						{
							if(!this.mergeItemStack(stack1, 0, 2, false)) return ItemStack.EMPTY;
						}
						else if(TileEntityFusionFurnace.isItemFuel(stack1))
						{
							boolean isEntry = FusionFurnaceRecipe.getInstance().compareItemStack(stack1, entry.getKey()) || FusionFurnaceRecipe.getInstance().compareItemStack(stack1, entry1.getKey());
							
							if(!isEntry && this.inventorySlots.get(2).getStack().getCount() >= 64)
							{
								if(!this.mergeItemStack(stack1, 0, 2, false)) return ItemStack.EMPTY;
							}
							else if(!this.mergeItemStack(stack1, 2, 3, false)) return ItemStack.EMPTY;
						}
						else if(index >= 4 && index < 31)
						{
							if(!this.mergeItemStack(stack1, 31, 40, false)) return ItemStack.EMPTY;
						}
						else if(index >= 31 && index < 40 && !this.mergeItemStack(stack1, 4, 31, false))
						{
							return ItemStack.EMPTY;
						}
					}
				}
			}
			else if(!this.mergeItemStack(stack1, 4, 40, false)) return ItemStack.EMPTY;
			
			if(stack1.isEmpty()) slot.putStack(ItemStack.EMPTY);
			else slot.onSlotChanged();
			
			if(stack1.getCount() == stack.getCount()) return ItemStack.EMPTY;
			slot.onTake(player, stack1);
		}
		return stack;
	}
}