package com.modding.forge.containers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.modding.forge.capability.provider.CapabilityAccessoryProvider;
import com.modding.forge.init.InitItems;
import com.modding.forge.items.ItemAccessory;
import com.modding.forge.items.interfaces.IAccessory.EnumAccessoryType;
import com.modding.forge.network.ModNetworkingManager;
import com.modding.forge.network.packets.CapabilityAccessoryPacket;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerAccessory extends ContainerPlayer
{
	public ContainerAccessory(EntityPlayer player)
	{
		super(player.inventory, !player.world.isRemote, player);
		
		IItemHandler handler = player.getCapability(CapabilityAccessoryProvider.INVENTORY_ACCESSORY_CAP, null);
		for(int i = 0; i < 3; i++)
    	{
    		if(i == 0)
    		{
    			this.addSlotToContainer(new SlotItemHandler(handler, i, 77, 8 + i * 18)
    			{
    				@Override
                    public boolean isItemValid(ItemStack stack)
                    {
                        boolean isAccessory = InitItems.isAccessory(stack);
                        if(isAccessory)
                        {
                        	ItemAccessory accessory = (ItemAccessory)stack.getItem();
                        	if(accessory.getAccessoryType() == EnumAccessoryType.NECKLACE) return true;
                        }
                        return false;
                    }
    				
    	            @Nullable
    	            @SideOnly(Side.CLIENT)
    	            public String getSlotTexture()
    	            {
    	                return "elders_reborn:gui/empty_necklace";
    	            }
    	            
    	            @Override
    	            public void onSlotChange(@Nonnull ItemStack p_75220_1_, @Nonnull ItemStack p_75220_2_)
    	            {
    	            	ModNetworkingManager.INSTANCE.sendToAll(new CapabilityAccessoryPacket(player.getEntityId(), player.serializeNBT()));
    	            }
    			});
    		}
    		else
    		{
    			this.addSlotToContainer(new SlotItemHandler(handler, i, 77, 8 + i * 18)
    			{
    				@Override
                    public boolean isItemValid(ItemStack stack)
                    {
                        boolean isAccessory = InitItems.isAccessory(stack);
                        if(isAccessory)
                        {
                        	ItemAccessory accessory = (ItemAccessory)stack.getItem();
                        	if(accessory.getAccessoryType() == EnumAccessoryType.RING) return true;
                        }
                        return false;
                    }
    				
    	            @Nullable
    	            @SideOnly(Side.CLIENT)
    	            public String getSlotTexture()
    	            {
    	                return "elders_reborn:gui/empty_ring";
    	            }
    	            
    	            @Override
    	            public void onSlotChange(@Nonnull ItemStack p_75220_1_, @Nonnull ItemStack p_75220_2_)
    	            {
    	            	ModNetworkingManager.INSTANCE.sendToAll(new CapabilityAccessoryPacket(player.getEntityId(), player.serializeNBT()));
    	            }
    			});
    		}
    	}
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
		Slot slot = (Slot)this.inventorySlots.get(index);
		if(slot != null && slot.getHasStack())
		{
			ItemStack stack = slot.getStack();
			if(InitItems.isAccessory(stack))
			{
				ItemAccessory accessory = (ItemAccessory)stack.getItem();
				int i = accessory.getAccessoryType().getIndex();
				boolean isEmpty = !this.inventorySlots.get(45 + i).getHasStack() || !this.inventorySlots.get(46 + i).getHasStack();
				
				if(index < 46 && isEmpty)
				{
					if(!this.mergeItemStack(stack, 46, 49, false)) return ItemStack.EMPTY;
				}
			}
		}
		return super.transferStackInSlot(playerIn, index);
    }
}
