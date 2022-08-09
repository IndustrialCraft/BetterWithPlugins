package com.github.industrialcraft.betterwithplugins.items;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class BasicCustomItem extends CustomItem{
    public final Material material;
    public final String name;
    public final List<String> lore;
    public BasicCustomItem(NamespacedKey key, Material material, String name, String... lore) {
        super(key);
        this.material = material;
        this.name = name;
        this.lore = Arrays.asList(lore);
    }
    @Override
    public ItemStack create(int count) {
        ItemStack itemStack = new ItemStack(this.material, count);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(this.name);
        meta.setLore(this.lore);
        setCustomItemData(meta);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public int getMaxStackSize() {
        return material.getMaxStackSize();
    }
}
