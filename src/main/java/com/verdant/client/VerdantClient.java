package com.verdant.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public final class VerdantClient implements ClientModInitializer {
    public static KeyBinding openGuiKey;
    @Override public void onInitializeClient() {
        VerdantConfig.load();
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.verdant.clickgui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, KeyBinding.Category.MISC));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKey.wasPressed()) if (client.currentScreen == null) client.setScreen(new VerdantClickGui());
        });
        WorldRenderEvents.AFTER_ENTITIES.register(VerdantEsp::render);
    }
}
