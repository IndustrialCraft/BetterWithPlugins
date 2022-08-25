package com.github.industrialcraft.betterwithplugins.gui;

import com.github.industrialcraft.betterwithplugins.items.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.function.Consumer;

public abstract class GUI<T> {
    private Consumer<Inventory> creationHandler;
    public GUI(Consumer<Inventory> creationHandler){
        this.creationHandler = creationHandler;
    }
    public Inventory create(T data){
        InventoryDataHolder inventoryDataHolder = new InventoryDataHolder(this, data);
        Inventory inventory = createEmpty(inventoryDataHolder);
        inventoryDataHolder.inventory = inventory;
        creationHandler.accept(inventory);
        return inventory;
    }
    protected abstract Inventory createEmpty(InventoryDataHolder<T> dataHolder);

    public boolean onClick(InventoryClickEvent event, CustomItem currentCustomItem, CustomItem cursorCustomItem){return false;}
    public boolean onDrag(InventoryDragEvent event, CustomItem cursorCustomItem){return false;}
    public void onClose(InventoryCloseEvent event){}

    public static class InventoryDataHolder<T> implements InventoryHolder{
        public final GUI gui;
        public T data;
        private Inventory inventory;
        InventoryDataHolder(GUI gui, T data) {
            this.gui = gui;
            this.data = data;
        }
        @Override
        public Inventory getInventory() {
            return inventory;
        }
    }
}
