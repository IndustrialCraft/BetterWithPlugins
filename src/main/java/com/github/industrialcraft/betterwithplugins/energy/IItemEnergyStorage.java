package com.github.industrialcraft.betterwithplugins.energy;

import org.bukkit.inventory.ItemStack;

public interface IItemEnergyStorage {
    default int takeEnergy(ItemStack item, int max){
        int storedEnergy = getStoredEnergy(item);
        int energyTaken = Math.min(storedEnergy, max);
        setStoredEnergy(item, storedEnergy-energyTaken);
        return energyTaken;
    }
    default int chargeEnergy(ItemStack item, int amount){
        int capacity = getEnergyCapacity(item);
        int newStoredEnergy = getStoredEnergy(item)+amount;
        setStoredEnergy(item, Math.min(newStoredEnergy, capacity));
        return Math.max(newStoredEnergy-capacity, 0);
    }
    default boolean takeIfEnough(ItemStack item, int amount){
        int storedEnergy = getStoredEnergy(item);
        if(storedEnergy >= amount){
            setStoredEnergy(item, storedEnergy-amount);
            return true;
        }
        return false;
    }

    int getEnergyCapacity(ItemStack item);

    int getStoredEnergy(ItemStack item);
    void setStoredEnergy(ItemStack item, int energy);
}
