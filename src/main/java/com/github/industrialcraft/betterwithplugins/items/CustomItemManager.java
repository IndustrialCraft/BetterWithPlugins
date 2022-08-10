package com.github.industrialcraft.betterwithplugins.items;

import com.github.industrialcraft.betterwithplugins.BWPMain;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class CustomItemManager implements Listener {
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player player){
            CustomItem customItem = BWPMain.getItemRegistry().fromStack(player.getItemInHand());
            if(customItem == null)
                return;
            customItem.onAttack(event, player);
        }
    }
    @EventHandler
    public void onEat(PlayerItemConsumeEvent event){
        CustomItem customItem = BWPMain.getItemRegistry().fromStack(event.getItem());
        if(customItem == null)
            return;
        customItem.onEat(event);
    }
    @EventHandler
    public void onEntityUse(PlayerInteractEntityEvent event){
        CustomItem customItem = BWPMain.getItemRegistry().fromStack(event.getPlayer().getInventory().getItem(event.getHand()));
        if(customItem == null)
            return;
        customItem.onEntityUse(event);
    }
    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event){
        CustomItem customItem = BWPMain.getItemRegistry().fromStack(event.getPlayer().getInventory().getItem(event.getHand()));
        if(customItem == null)
            return;
        customItem.onPlace(event);
    }
    @EventHandler
    public void onItemUse(PlayerInteractEvent event){
        if(event.hasItem()) {
            CustomItem customItem = BWPMain.getItemRegistry().fromStack(event.getPlayer().getInventory().getItem(event.getHand()));
            if (customItem == null)
                return;
            customItem.onItemUse(event);
        }
    }
}
