package com.github.industrialcraft.betterwithplugins.events;

import com.github.industrialcraft.betterwithplugins.BWPMain;
import com.github.industrialcraft.betterwithplugins.items.IGUIItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

public class GuiItemMoveCanceller implements Listener {
    @EventHandler
    public void onItemMove(InventoryMoveItemEvent event){
        if(BWPMain.getItemRegistry().fromStack(event.getItem()) instanceof IGUIItem)
            event.setCancelled(true);
    }
}
