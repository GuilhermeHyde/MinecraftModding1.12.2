package com.modding.forge.blocks.tilentities;

import com.modding.forge.blocks.FusionFurnaceBlock;
import com.modding.forge.blocks.recipes.FusionFurnaceRecipe;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityFusionFurnace extends TileEntity implements ITickable
{
	private ItemStackHandler handler = new ItemStackHandler(4);
	private int heat, castingProcess, meltingProcess, maxCasting, maxMelting;
	private String tileEntityName;
	private ItemStack smelting = ItemStack.EMPTY;
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
		else return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(handler);
		return super.getCapability(capability, facing);
	}
	
	public void setName(String name)
	{
		this.tileEntityName = name;
	}
	
	public boolean hasCustomName()
	{
		return this.tileEntityName != null && !this.tileEntityName.isEmpty();
	}
	
	@Override
	public ITextComponent getDisplayName()
	{
		return this.hasCustomName() ? new TextComponentString(this.tileEntityName) : new TextComponentTranslation("container.fusion_furnace");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		this.meltingProcess = compound.getInteger("MeltingProcess");
		this.castingProcess = compound.getInteger("CastingProcess");
		this.maxMelting = compound.getInteger("MaxMelting");
		this.maxCasting = compound.getInteger("MaxCasting");
		this.heat = getItemBurnTime((ItemStack)this.handler.getStackInSlot(2));
		if(compound.hasKey("CustomName", 8)) setName(compound.getString("CustomName"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setTag("Inventory", this.handler.serializeNBT());
		compound.setInteger("MeltingProcess", (short)this.meltingProcess);
		compound.setInteger("CastingProcess", (short)this.castingProcess);
		compound.setInteger("MaxMelting", (short)this.maxMelting);
		compound.setInteger("MaxCasting", (short)this.maxCasting);
		if(this.hasCustomName()) compound.setString("CustomName", this.tileEntityName);
		return compound;
	}
	
	public boolean isHeating()
	{
		return this.heat > 0;
	}
	
	@SideOnly(Side.CLIENT)
	public static boolean isHeating(TileEntityFusionFurnace tileEntity)
	{
		return tileEntity.getField(4) > 0;
	}

	@Override
	public void update()
	{
		if(isHeating())
		{
			FusionFurnaceBlock.setState(true, world, pos);
		}
		
		ItemStack[] inputs = new ItemStack[] {handler.getStackInSlot(0), handler.getStackInSlot(1)};
		ItemStack fuel = this.handler.getStackInSlot(2);
		
		if(isHeating() || !fuel.isEmpty() && !this.handler.getStackInSlot(0).isEmpty() || this.handler.getStackInSlot(1).isEmpty())
		{
			if(!isHeating() && canSmelt())
			{
				this.heat = getItemBurnTime(fuel);
				
				if(isHeating() && !fuel.isEmpty())
				{
					Item item = fuel.getItem();
					fuel.shrink(1);
					
					if(fuel.isEmpty())
					{
						ItemStack item1 = item.getContainerItem(fuel);
						this.handler.setStackInSlot(2, item1);
					}
				}
			}
		}
		
		if(isHeating() && canSmelt())
		{
			this.meltingProcess++;
			if(this.meltingProcess >= 8)
			{
				--this.heat;
				this.meltingProcess = 0;
				this.castingProcess++;
			}
			
			if(this.castingProcess >= 8)
			{
				if(handler.getStackInSlot(3).getCount() > 0) handler.getStackInSlot(3).grow(1);
				else handler.insertItem(3, this.smelting, false);
				this.smelting = ItemStack.EMPTY;
				this.castingProcess = 0;
				return;
			}
		}
		else
		{
			if(isHeating() && canSmelt())
			{
				ItemStack output = FusionFurnaceRecipe.getInstance().getRecipesResult(handler.getStackInSlot(0), handler.getStackInSlot(1));
				if(!output.isEmpty())
				{
					this.smelting = output;
					meltingProcess++;
					inputs[0].shrink(1);
					inputs[1].shrink(1);
					handler.setStackInSlot(0, inputs[0]);
					handler.setStackInSlot(1, inputs[1]);
				}
			}
		}
	}
	
	private boolean canSmelt()
	{
		if(((ItemStack)this.handler.getStackInSlot(0)).isEmpty() || ((ItemStack)this.handler.getStackInSlot(1)).isEmpty()) return false;
		else
		{
			ItemStack result = FusionFurnaceRecipe.getInstance().getRecipesResult((ItemStack)this.handler.getStackInSlot(0), (ItemStack)this.handler.getStackInSlot(1));
			if(result.isEmpty()) return false;
			else
			{
				ItemStack output = (ItemStack)this.handler.getStackInSlot(3);
				if(output.isEmpty()) return false;
				if(!output.isItemEqual(result)) return false;
				int res = output.getCount() + result.getCount();
				return res <= 64 && res <= output.getMaxStackSize();
			}
		}
	}

	private static int getItemBurnTime(ItemStack fuel)
	{
		if(fuel.isEmpty()) return 0;
		else 
		{
			Item item = fuel.getItem();

			if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR) 
			{
				Block block = Block.getBlockFromItem(item);

				if (block == Blocks.WOODEN_SLAB) return 150;
				if (block.getDefaultState().getMaterial() == Material.WOOD) return 300;
				if (block == Blocks.COAL_BLOCK) return 16000;
			}

			if (item instanceof ItemTool && "WOOD".equals(((ItemTool)item).getToolMaterialName())) return 200;
			if (item instanceof ItemSword && "WOOD".equals(((ItemSword)item).getToolMaterialName())) return 200;
			if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe)item).getMaterialName())) return 200;
			if (item == Items.STICK) return 100;
			if (item == Items.COAL) return 1600;
			if (item == Items.LAVA_BUCKET) return 20000;
			if (item == Item.getItemFromBlock(Blocks.SAPLING)) return 100;
			if (item == Items.BLAZE_ROD) return 2400;

			return ForgeEventFactory.getItemBurnTime(fuel);
		}
	}
	
	public static boolean isItemFuel(ItemStack stack)
	{
		return getItemBurnTime(stack) > 0;
	}
	
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}
	
	public int getField(int id)
	{
		switch(id)
		{
		case 0:
			return this.castingProcess;
		case 1:
			return this.meltingProcess;
		case 2:
			return this.maxCasting;
		case 3:
			return this.maxMelting;
		case 4:
			return this.heat;
		default:
			return 0;
		}
	}
	
	public void setField(int id, int value)
	{
		switch(id)
		{
		case 0:
			this.castingProcess = value;
			break;
		case 1:
			this.meltingProcess = value;
			break;
		case 2:
			this.maxCasting = value;
			break;
		case 3:
			this.maxMelting = value;
			break;
		case 4:
			this.heat = value;
			break;
		}
	}
}
