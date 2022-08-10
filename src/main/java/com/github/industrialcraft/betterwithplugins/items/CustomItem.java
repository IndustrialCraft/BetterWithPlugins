package com.github.industrialcraft.betterwithplugins.items;

import com.github.industrialcraft.betterwithplugins.BWPMain;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public abstract class CustomItem {
    private final NamespacedKey key;
    private int customModelData;
    public CustomItem(NamespacedKey key) {
        this.key = key;
        this.customModelData = -1;
    }

    public abstract ItemStack create(int count);
    public ItemStack create(){
        return create(1);
    }

    public abstract int getMaxStackSize();

    public NamespacedKey getKey(){
        return this.key;
    }

    public CustomItem register(){
        if(this.customModelData != -1)
            throw new IllegalStateException("Item " + key.toString() + " is already registered");
        this.customModelData = BWPMain.getItemRegistry().register(this);
        return this;
    }
    public int getCustomModelData() {
        return customModelData;
    }

    public void onItemUse(PlayerInteractEvent event){}
    public void onPlace(BlockPlaceEvent event){}
    public void onEntityUse(PlayerInteractEntityEvent event){}
    public void onEat(PlayerItemConsumeEvent event){}
    public void onAttack(EntityDamageByEntityEvent event, Player player){}

    protected void setCustomItemData(ItemMeta meta){
        meta.getPersistentDataContainer().set(BWPMain.getKeys().CUSTOM_ID_KEY, PersistentDataType.STRING, this.key.toString());
        meta.setCustomModelData(this.customModelData);
    }
    public static String getCustomItemId(ItemStack item){
        if(!item.hasItemMeta())
            return null;
        return getCustomItemId(item.getItemMeta());
    }
    public static String getCustomItemId(ItemMeta meta){
        return meta.getPersistentDataContainer().get(BWPMain.getKeys().CUSTOM_ID_KEY, PersistentDataType.STRING);
    }
}
