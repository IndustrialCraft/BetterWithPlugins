package com.github.industrialcraft.betterwithplugins.energy;

import com.github.industrialcraft.betterwithplugins.BWPMain;
import com.github.industrialcraft.betterwithplugins.items.BasicCustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class EnergyStoringItem extends BasicCustomItem implements IItemEnergyStorage{
    public final int capacity;
    public EnergyStoringItem(NamespacedKey key, int capacity, Material material, String name, String... lore) {
        super(key, material, name, lore);
        this.capacity = capacity;
    }
    @Override
    public int getEnergyCapacity(ItemStack item) {
        return capacity;
    }
    @Override
    public int getStoredEnergy(ItemStack stack) {
        if(!stack.hasItemMeta())
            return 0;
        ItemMeta meta = stack.getItemMeta();
        return meta.getPersistentDataContainer().getOrDefault(BWPMain.getKeys().STORED_ENERGY, PersistentDataType.INTEGER, 0);
    }
    @Override
    public void setStoredEnergy(ItemStack stack, int energy) {
        if(!stack.hasItemMeta())
            return;
        ItemMeta meta = stack.getItemMeta();
        List<String> lore = meta.hasLore()?meta.getLore(): Arrays.asList("");
        lore.set(0, energy + "/" + capacity);
        meta.setLore(lore);
        meta.getPersistentDataContainer().set(BWPMain.getKeys().STORED_ENERGY, PersistentDataType.INTEGER, energy);
        stack.setItemMeta(meta);
    }
}
