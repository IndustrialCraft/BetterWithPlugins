package com.github.industrialcraft.betterwithplugins.util;

import com.github.industrialcraft.betterwithplugins.BWPMain;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class Keys {
    public final NamespacedKey CUSTOM_ITEM_KEY;
    public Keys(BWPMain plugin) {
        this.CUSTOM_ITEM_KEY = plugin.createKey("custom_item_key");
    }
}
