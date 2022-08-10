package com.github.industrialcraft.betterwithplugins.util;

import com.github.industrialcraft.betterwithplugins.BWPMain;
import org.bukkit.NamespacedKey;

public class Keys {
    public final NamespacedKey CUSTOM_ID_KEY;
    public Keys(BWPMain plugin) {
        this.CUSTOM_ID_KEY = plugin.createKey("custom_id");
    }
}
