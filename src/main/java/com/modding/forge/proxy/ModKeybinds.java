package com.modding.forge.proxy;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ModKeybinds
{
    public static KeyBinding[] keyBindings = new KeyBinding[1];
    public static KeyBinding keyBindingAccessories = new KeyBinding("key.open_accessories.desc", Keyboard.KEY_I, "key.category.custom_inventory");
    
    public static void initialization()
    {
        keyBindings[0] = keyBindingAccessories;

        for (int i = 0; i < keyBindings.length; ++i)
        {
            ClientRegistry.registerKeyBinding(keyBindings[i]);
        }
    }
}
