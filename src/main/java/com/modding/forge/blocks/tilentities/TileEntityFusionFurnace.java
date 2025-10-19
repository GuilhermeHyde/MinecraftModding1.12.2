package com.modding.forge.blocks.tilentities;

import javax.annotation.Nonnull;

import com.modding.forge.blocks.FusionFurnaceBlock;
import com.modding.forge.blocks.recipes.FusionFurnaceRecipe;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityXPOrb;
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
import net.minecraft.util.math.MathHelper;
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
	public final ItemStackHandler handler = new ItemStackHandler(4)
	{
	    @Override
	    public boolean isItemValid(int slot, @Nonnull ItemStack stack)
	    {
	    	switch(slot)
	    	{
	    	case 0:
	    		return true;
	    	case 1:
	    		return true;
	    	case 2:
	    		return isItemFuel(stack);
	    	case 3:
	    		return false;
	    		default:
	    			return true;
	    	}
	    }
	};
	private int heat, maxHeat = 5000, castingProcess, meltingProcess, maxCasting, maxMelting, removeCount;
	private String tileEntityName;
	
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
		this.heat = compound.getInteger("Heat");
		this.meltingProcess = compound.getInteger("MeltingProcess");
		this.castingProcess = compound.getInteger("CastingProcess");
		this.maxMelting = compound.getInteger("MaxMelting");
		this.maxCasting = compound.getInteger("MaxCasting");
		this.maxHeat = compound.getInteger("MaxHeat");
		this.removeCount = compound.getInteger("RemoveCount");
		if(compound.hasKey("CustomName", 8)) setName(compound.getString("CustomName"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setTag("Inventory", this.handler.serializeNBT());
		compound.setInteger("Heat", (short)this.heat);
		compound.setInteger("MeltingProcess", (short)this.meltingProcess);
		compound.setInteger("CastingProcess", (short)this.castingProcess);
		compound.setInteger("MaxMelting", (short)this.maxMelting);
		compound.setInteger("MaxCasting", (short)this.maxCasting);
		compound.setInteger("MaxHeat", (short)this.maxHeat);
		compound.setInteger("RemoveCount", (short)this.removeCount);
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
		ItemStack[] input = new ItemStack[] {this.handler.getStackInSlot(0), this.handler.getStackInSlot(1)};
		ItemStack output = FusionFurnaceRecipe.getInstance().getRecipesResult(input[0], input[1], 5000);
		ItemStack slotFuel = this.handler.getStackInSlot(2);
		boolean flag = false;
		
		if(this.isHeating())
		{
			FusionFurnaceBlock.setState(true, world, pos);
		}
		
		int buffer = this.heat;
		this.heat = Math.max(0, Math.min(this.heat, this.maxHeat));
		if(this.heat != buffer) flag = true;
		
		if(this.heat < this.maxHeat && !slotFuel.isEmpty())
		{
			int fuel = getItemBurnTime(slotFuel);
			if(fuel > 0)
			{
				Item item = slotFuel.getItem();
				this.heat += fuel;
				slotFuel.shrink(1);
				flag = true;
				
				if(slotFuel.isEmpty())
				{
					ItemStack item1 = item.getContainerItem(slotFuel);
					this.handler.setStackInSlot(2, item1);
					flag = true;
				}
				else if(item == Items.LAVA_BUCKET)
				{
					this.handler.setStackInSlot(2, new ItemStack(Items.BUCKET));
					flag = true;
				}
			}
		}
		
		if(this.isHeating() && this.canSmelt())
		{
			this.maxCasting = FusionFurnaceRecipe.getInstance().getCastingProcess(output);
			this.maxMelting = FusionFurnaceRecipe.getInstance().getCastingProcess(output) * 4;
			
			this.meltingProcess++;
			if(this.meltingProcess >= this.maxMelting)
			{
				this.heat -= 50;
				this.castingProcess++;
				this.meltingProcess = 0;
			}
			
			if(this.castingProcess >= this.maxCasting)
			{	
				if(this.handler.getStackInSlot(3).getCount() > 0) this.handler.getStackInSlot(3).grow(1);
				else if(!output.isEmpty()) this.handler.insertItem(3, output.copy(), false);
				
				input[0].shrink(1);
				input[1].shrink(1);
				this.handler.setStackInSlot(0, input[0]);
				this.handler.setStackInSlot(1, input[1]);
				
				this.removeCount++;
				this.castingProcess = 0;
			}
		}
		else
		{
			this.castingProcess = 0;
			this.meltingProcess = 0;
		}
		
		if(this.removeCount > 0)
		{
			int i = this.removeCount;
			float f = FusionFurnaceRecipe.getInstance().getCraftingExp(output);
			
			if(f == 0.0F) i = 0;
			else if(f < 1.0F)
			{
				int j = MathHelper.floor((float)i * f);
				if(j < MathHelper.ceil((float)i * f) && Math.random() < (double)((float)i * f - (float)j)) j++;
				i = j;
			}
			
			if(this.handler.getStackInSlot(3).isEmpty())
			{
				if(!world.isRemote)
				{
					int k;
					while(i > 0)
					{
						k = EntityXPOrb.getXPSplit(i);
						i -= k;
						world.spawnEntity(new EntityXPOrb(world, pos.getX(), pos.getY() + 0.5D, pos.getZ() + 0.5D, k));
					}
				}
				this.removeCount = 0;
			}
		}
		
		if(flag) this.markDirty();
	}
	
	public void onCrafting(ItemStack stack)
	{
		if(!world.isRemote)
		{
			int i = this.removeCount;
			float f = FusionFurnaceRecipe.getInstance().getCraftingExp(stack);
			
			if(f == 0.0F) i = 0;
			else if(f < 1.0F)
			{
				int j = MathHelper.floor((float)i * f);
				if(j < MathHelper.ceil((float)i * f) && Math.random() < (double)((float)i * f - (float)j)) j++;
				i = j;
			}
			
			int k = 0;
			while(i > 0)
			{
				k = EntityXPOrb.getXPSplit(i);
				i -= k;
				world.spawnEntity(new EntityXPOrb(world, pos.getX(), pos.getY(), pos.getZ(), k));
			}
		}
		this.removeCount = 0;
	}
	
	private boolean canSmelt()
	{
		if(((ItemStack)this.handler.getStackInSlot(0)).isEmpty() || ((ItemStack)this.handler.getStackInSlot(1)).isEmpty()) return false;
		else
		{
			ItemStack result = FusionFurnaceRecipe.getInstance().getRecipesResult((ItemStack)this.handler.getStackInSlot(0), (ItemStack)this.handler.getStackInSlot(1), this.getField(4));
			if(result.isEmpty()) return false;
			else
			{
				ItemStack output = (ItemStack)this.handler.getStackInSlot(3);
				if(output.isEmpty()) return true;
				if(!ItemStack.areItemsEqual(output, result)) return false;
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

				if (block == Blocks.WOODEN_SLAB) return 15;
				if (block.getDefaultState().getMaterial() == Material.WOOD) return 15;
				if (block == Blocks.COAL_BLOCK) return 100;
			}

			if (item instanceof ItemTool && "WOOD".equals(((ItemTool)item).getToolMaterialName())) return 5;
			if (item instanceof ItemSword && "WOOD".equals(((ItemSword)item).getToolMaterialName())) return 5;
			if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe)item).getMaterialName())) return 5;
			if (item == Items.STICK) return 5;
			if (item == Items.COAL) return 25;
			if (item == Items.LAVA_BUCKET) return 500;
			if (item == Item.getItemFromBlock(Blocks.SAPLING)) return 5;
			if (item == Items.BLAZE_ROD) return 750;

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
		case 5:
			return this.maxHeat;
		case 6:
			return this.removeCount;
		default:
			return 0;
		}
	}
	
	public void setField(int id, int value)
	{
		switch(id)
		{
		case 0:
			if(value >= 0) this.castingProcess = value;
			break;
		case 1:
			if(value >= 0) this.meltingProcess = value;
			break;
		case 2:
			if(value >= 0) this.maxCasting = value;
			break;
		case 3:
			if(value >= 0) this.maxMelting = value;
			break;
		case 4:
			if(value >= 0) this.heat = value;
			break;
		case 5:
			if(value >= 0) this.maxHeat = value;
		case 6:
			if(value >= 0) this.removeCount = value;
			break;
		}
	}
}
