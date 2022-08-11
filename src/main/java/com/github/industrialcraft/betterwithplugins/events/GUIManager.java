package com.github.industrialcraft.betterwithplugins.events;

import com.github.industrialcraft.betterwithplugins.BWPMain;
import com.github.industrialcraft.betterwithplugins.gui.GUI;
import com.github.industrialcraft.betterwithplugins.items.CustomItem;
import com.github.industrialcraft.betterwithplugins.items.IGUIItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.InventoryHolder;

public class GUIManager implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        CustomItem customClicked = BWPMain.getItemRegistry().fromStack(event.getCurrentItem());
        if(customClicked != null && customClicked instanceof IGUIItem) {
            event.setCancelled(true);
            return;
        }
        if(event.getClickedInventory() == null)
            return;
        InventoryHolder holder = event.getClickedInventory().getHolder();
        if(holder == null)
            return;
        if(holder instanceof GUI.InventoryDataHolder dataHolder){
            GUI gui = dataHolder.gui;
            if(gui.onClick(event, customClicked))
                event.setCancelled(true);
        }
    }
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event){
        CustomItem customCursor = BWPMain.getItemRegistry().fromStack(event.getOldCursor());
        if(customCursor != null && customCursor instanceof IGUIItem) {
            event.setCancelled(true);
            return;
        }
        InventoryHolder holder = event.getInventory().getHolder();
        if(holder == null)
            return;
        if(holder instanceof GUI.InventoryDataHolder dataHolder){
            GUI gui = dataHolder.gui;
            if(gui.onDrag(event, customCursor))
                event.setCancelled(true);
        }
    }
    @EventHandler
    public void onClose(InventoryCloseEvent event){
        InventoryHolder holder = event.getInventory().getHolder();
        if(holder == null)
            return;
        if(holder instanceof GUI.InventoryDataHolder dataHolder){
            GUI gui = dataHolder.gui;
            gui.onClose(event);
        }
    }
}
