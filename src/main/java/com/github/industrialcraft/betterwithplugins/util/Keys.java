package com.github.industrialcraft.betterwithplugins.util;

import com.github.industrialcraft.betterwithplugins.BWPMain;
import org.bukkit.NamespacedKey;

public class Keys {
    public final NamespacedKey CUSTOM_ID;
    public final NamespacedKey STORED_ENERGY;
    public Keys(BWPMain plugin) {
        this.CUSTOM_ID = plugin.createKey("custom_id");
        this.STORED_ENERGY = plugin.createKey("stored_energy");
    }
}
