package com.github.industrialcraft.betterwithplugins.gui;

import com.github.industrialcraft.betterwithplugins.items.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public abstract class GUI {
    private IInventoryCreator creator;
    public GUI(String name, int size){
        this.creator = new SizedInventoryCreator(size, name);
    }
    public GUI(String name, InventoryType type){
        this.creator = new TypedInventoryCreator(type, name);
    }
    public Inventory create(){
        InventoryDataHolder inventoryDataHolder = new InventoryDataHolder(this);
        Inventory inventory = creator.create(inventoryDataHolder);
        inventoryDataHolder.inventory = inventory;
        return inventory;
    }
    public void open(Player player){
        player.openInventory(create());
    }

    boolean onClick(InventoryClickEvent event, CustomItem currentCustomItem){return false;}
    boolean onDrag(InventoryDragEvent event, CustomItem cursorCustomItem){return false;}
    void onClose(InventoryCloseEvent event){}

    class InventoryDataHolder implements InventoryHolder{
        public final GUI gui;
        Inventory inventory;
        InventoryDataHolder(GUI gui) {
            this.gui = gui;
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
