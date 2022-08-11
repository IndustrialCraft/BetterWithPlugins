package com.github.industrialcraft.betterwithplugins.items;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class BasicGuiItem extends CustomItem implements IGUIItem{
    public BasicGuiItem(NamespacedKey key) {
        super(key);
    }
    @Override
    public ItemStack create(int count) {
        ItemStack itemStack = new ItemStack(Material.CLOCK, count);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(" ");
        setCustomItemData(meta);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
