package com.github.industrialcraft.betterwithplugins.block;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public abstract class BasicTickingBlock extends CustomBlock implements ITickingBlock{
    public BasicTickingBlock(NamespacedKey key, Material material) {
        super(key, material);
    }
}
