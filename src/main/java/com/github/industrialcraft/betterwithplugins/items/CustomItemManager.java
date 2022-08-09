package com.github.industrialcraft.betterwithplugins.items;

import com.github.industrialcraft.betterwithplugins.BWPMain;
import com.github.industrialcraft.betterwithplugins.items.handler.*;
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
            if(customItem instanceof IAttackHandler attackHandler){
                attackHandler.onAttack(event, player);
            }
        }
    }
    @EventHandler
    public void onEat(PlayerItemConsumeEvent event){
        CustomItem customItem = BWPMain.getItemRegistry().fromStack(event.getItem());
        if(customItem == null)
            return;
        if(customItem instanceof IEatHandler eatHandler){
            eatHandler.onEat(event);
        }
    }
    @EventHandler
    public void onEntityUse(PlayerInteractEntityEvent event){
        CustomItem customItem = BWPMain.getItemRegistry().fromStack(event.getPlayer().getInventory().getItem(event.getHand()));
        if(customItem == null)
            return;
        if(customItem instanceof IEntityUseHandler entityUseHandler){
            entityUseHandler.onEntityUse(event);
        }
    }
    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event){
        CustomItem customItem = BWPMain.getItemRegistry().fromStack(event.getPlayer().getInventory().getItem(event.getHand()));
        if(customItem == null)
            return;
        if(customItem instanceof IPlaceHandler placeHandler){
            placeHandler.onPlace(event);
        }
    }
    @EventHandler
    public void onItemUse(PlayerInteractEvent event){
        if(event.hasItem() && event.hasBlock()) {
            CustomItem customItem = BWPMain.getItemRegistry().fromStack(event.getPlayer().getInventory().getItem(event.getHand()));

            if (customItem == null)
                return;
            if (customItem instanceof IUseHandler useHandler) {
                useHandler.onItemUse(event);
            }
        }
    }
}
