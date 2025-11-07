package com.modding.forge.containers;

import com.modding.forge.containers.inventory.IInventoryAccessory;
import com.modding.forge.init.InitItems;
import com.modding.forge.items.IAccessory.EnumAccessoryType;
import com.modding.forge.items.ItemAccessory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerInventoryAccessory extends ContainerPlayer
{
	public ContainerInventoryAccessory(IInventoryAccessory inventory, EntityPlayer player)
	{
		super(player.inventory, !player.world.isRemote, player);
		this.inventorySlots.removeIf(entry -> entry.getSlotIndex() == 40);
		this.inventorySlots.set(40, this.addSlotToContainer(new Slot(player.inventory, 40, 77, 62)
		{
			@Override
		    @SideOnly(Side.CLIENT)
		    public String getSlotTexture()
		    {
		        return "elders_reborn:items/empty_hand";
		    }
		}));
		
		for(int i = 0; i < 3; i++)
		{
			if(i == 0)
			{
				this.addSlotToContainer(new Slot(inventory, i, 77, 8)
				{
					private EnumAccessoryType accessoryType = EnumAccessoryType.NECKLACE;
					
					@Override
					public boolean isItemValid(ItemStack stack)
					{
						boolean flag = InitItems.REGISTER_ACCESSORY.contains(stack.getItem());
						
						if(flag)
						{
							ItemAccessory accessory = (ItemAccessory)stack.getItem();
							if(accessory.getAccessoryType() == this.accessoryType) return true;
						}
						return false;
					}
					
					@Override
				    @SideOnly(Side.CLIENT)
				    public String getSlotTexture()
				    {
				        return "elders_reborn:items/empty_necklace";
				    }
				});
			}
			else
			{
				this.addSlotToContainer(new Slot(inventory, i, 77, 8 + i * 18)
				{	
					private EnumAccessoryType accessoryType = EnumAccessoryType.RING;
						
					@Override
					public boolean isItemValid(ItemStack stack)
					{
						boolean flag = InitItems.REGISTER_ACCESSORY.contains(stack.getItem());
							
						if(flag)
						{
							ItemAccessory accessory = (ItemAccessory)stack.getItem();
							if(accessory.getAccessoryType() == this.accessoryType) return true;
						}
						return false;
					}
						
					@Override
					@SideOnly(Side.CLIENT)
					public String getSlotTexture()
					 {
					      return "elders_reborn:items/empty_ring";
					 }
				});
			}
		}
	}
}
