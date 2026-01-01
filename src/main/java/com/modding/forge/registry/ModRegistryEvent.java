package com.modding.forge.registry;

import com.modding.forge.Main;
import com.modding.forge.init.InitBlocks;
import com.modding.forge.init.InitItems;
import com.modding.forge.network.ModNetworkingManager;
import com.modding.forge.capability.CapabilityAccessory;
import com.modding.forge.capability.CapabilityAttribute;
import com.modding.forge.capability.CapabilityEquipment;
import com.modding.forge.capability.CapabilityLevel;
import com.modding.forge.capability.CapabilityStats;
import com.modding.forge.capability.CapabilityWeapon;
import com.modding.forge.gui.GuiHandler;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@EventBusSubscriber
public class ModRegistryEvent
{
	@SubscribeEvent
	public static void onRegisterBlockEvent(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(InitBlocks.getBlockList());
		TileEntityRegistry.register();
	}
	
	@SubscribeEvent
	public static void onRegisterItemEvent(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(InitItems.getItemList());
		event.getRegistry().registerAll(InitItems.getItemAccessoryList());
		event.getRegistry().registerAll(InitBlocks.getItemBlockList());
	}
	
	@SubscribeEvent
	public static void onTextureStitch(TextureStitchEvent.Pre event)
	{
	    event.getMap().registerSprite(new ResourceLocation("elders_reborn:gui/empty_ring"));
	    event.getMap().registerSprite(new ResourceLocation("elders_reborn:gui/empty_necklace"));
	}
	
	public static void preInit(FMLPreInitializationEvent event)
	{
		
	}
	
	public static void init(FMLInitializationEvent event)
	{
		ModNetworkingManager.initialization();
		CapabilityManager.INSTANCE.register(CapabilityStats.class, new Capability.IStorage<CapabilityStats>()
		{
			@Override
			public NBTBase writeNBT(Capability<CapabilityStats> capability, CapabilityStats instance, EnumFacing side)
			{
				return instance.serializeNBT();
			}
			
			@Override
			public void readNBT(Capability<CapabilityStats> capability, CapabilityStats instance, EnumFacing side, NBTBase nbt)
			{
				if(nbt instanceof NBTTagCompound) instance.deserializeNBT((NBTTagCompound)nbt);
			}
		}, CapabilityStats::new);
		
		CapabilityManager.INSTANCE.register(CapabilityLevel.class, new Capability.IStorage<CapabilityLevel>()
		{
			@Override
			public NBTBase writeNBT(Capability<CapabilityLevel> capability, CapabilityLevel instance, EnumFacing side)
			{
				return instance.serializeNBT();
			}
			
			@Override
			public void readNBT(Capability<CapabilityLevel> capability, CapabilityLevel instance, EnumFacing side, NBTBase nbt)
			{
				if(nbt instanceof NBTTagCompound) instance.deserializeNBT((NBTTagCompound)nbt);
			}
		}, CapabilityLevel :: new);
		
		CapabilityManager.INSTANCE.register(CapabilityAccessory.class, new Capability.IStorage<CapabilityAccessory>()
		{
			@Override
			public NBTBase writeNBT(Capability<CapabilityAccessory> capability, CapabilityAccessory instance, EnumFacing side)
			{
				return instance.serializeNBT();
			}
			
			@Override
			public void readNBT(Capability<CapabilityAccessory> capability, CapabilityAccessory instance, EnumFacing side, NBTBase nbt)
			{
				if(nbt instanceof NBTTagCompound) instance.deserializeNBT((NBTTagCompound)nbt);
			}
			
		}, CapabilityAccessory :: new);
		
		CapabilityManager.INSTANCE.register(CapabilityWeapon.class, new Capability.IStorage<CapabilityWeapon>()
		{
			@Override
			public NBTBase writeNBT(Capability<CapabilityWeapon> capability, CapabilityWeapon instance, EnumFacing side)
			{
				return instance.serializeNBT();
			}
			
			@Override
			public void readNBT(Capability<CapabilityWeapon> capability, CapabilityWeapon instance, EnumFacing side, NBTBase nbt)
			{
				if(nbt instanceof NBTTagCompound) instance.deserializeNBT((NBTTagCompound)nbt);
			}
		}, CapabilityWeapon :: new);
		
		CapabilityManager.INSTANCE.register(CapabilityEquipment.class, new Capability.IStorage<CapabilityEquipment>()
		{
			@Override
			public NBTBase writeNBT(Capability<CapabilityEquipment> capability, CapabilityEquipment instance, EnumFacing side)
			{
				return instance.serializeNBT();
			}
			
			@Override
			public void readNBT(Capability<CapabilityEquipment> capability, CapabilityEquipment instance, EnumFacing side, NBTBase nbt)
			{
				if(nbt instanceof NBTTagCompound) instance.deserializeNBT((NBTTagCompound)nbt);
			}
		}, CapabilityEquipment :: new);
		
		CapabilityManager.INSTANCE.register(CapabilityAttribute.class, new Capability.IStorage<CapabilityAttribute>()
		{
			@Override
			public NBTBase writeNBT(Capability<CapabilityAttribute> capability, CapabilityAttribute instance, EnumFacing side)
			{
				return instance.serializeNBT();
			}
			
			@Override
			public void readNBT(Capability<CapabilityAttribute> capability, CapabilityAttribute instance, EnumFacing side, NBTBase nbt)
			{
				if(nbt instanceof NBTTagCompound) instance.deserializeNBT((NBTTagCompound)nbt);
			}
		}, CapabilityAttribute :: new);
		
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());
	}
	
	public static void postInit(FMLPostInitializationEvent event)
	{
		
	}
}
