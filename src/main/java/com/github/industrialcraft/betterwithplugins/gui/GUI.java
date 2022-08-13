package com.github.industrialcraft.betterwithplugins.gui;

import com.github.industrialcraft.betterwithplugins.items.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.function.Consumer;

public class GUI<T> {
    private IInventoryCreator creator;
    private Consumer<Inventory> creationHandler;
    public GUI(String name, int size, Consumer<Inventory> creationHandler){
        this.creationHandler = creationHandler;
        this.creator = new SizedInventoryCreator(size, name);
    }
    public GUI(String name, InventoryType type, Consumer<Inventory> creationHandler){
        this.creationHandler = creationHandler;
        this.creator = new TypedInventoryCreator(type, name);
    }
    public Inventory create(T data){
        InventoryDataHolder inventoryDataHolder = new InventoryDataHolder(this, data);
        Inventory inventory = creator.create(inventoryDataHolder);
        inventoryDataHolder.inventory = inventory;
        creationHandler.accept(inventory);
        return inventory;
    }

    public boolean onClick(InventoryClickEvent event, CustomItem currentCustomItem, CustomItem cursorCustomItem){return false;}
    public boolean onDrag(InventoryDragEvent event, CustomItem cursorCustomItem){return false;}
    public void onClose(InventoryCloseEvent event){}

    public class InventoryDataHolder implements InventoryHolder{
        public final GUI gui;
        public final T data;
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

    private interface IInventoryCreator {
        Inventory create(InventoryHolder holder);
    }
    private record SizedInventoryCreator(int size, String name) implements IInventoryCreator {
        @Override
        public Inventory create(InventoryHolder holder) {
            return Bukkit.createInventory(holder, size, name);
        }
    }
    private record TypedInventoryCreator(InventoryType type, String name) implements IInventoryCreator {
        private TypedInventoryCreator {
            if (!type.isCreatable())
                throw new IllegalArgumentException("type " + type.name() + " is not creatable");
        }
        @Override
        public Inventory create(InventoryHolder holder) {
            return Bukkit.createInventory(holder, type, name);
        }
    }
}
