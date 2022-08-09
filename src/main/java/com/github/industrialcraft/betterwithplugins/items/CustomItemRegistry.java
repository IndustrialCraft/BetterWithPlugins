package com.github.industrialcraft.betterwithplugins.items;

import com.github.industrialcraft.betterwithplugins.BWPMain;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomItemRegistry {
    private HashMap<String,CustomItem> customItems;
    public CustomItemRegistry(){
        this.customItems = new HashMap<>();
        this.creatableItemsCache = null;
    }
    public int register(CustomItem customItem){
        String id = customItem.getKey().toString();
        if(customItems.containsKey(id))
            throw new IllegalStateException("Custom item " + id + " is already registered.");
        this.customItems.put(id, customItem);
        this.creatableItemsCache = null;
        return BWPMain.getCustomModelDataAssigner().getFor(customItem);
    }
    public CustomItem fromStack(ItemStack stack){
        String id = CustomItem.getCustomItemId(stack);
        return customItems.get(id);
    }
    public CustomItem fromId(String id){
        return customItems.get(id);
    }
    public void clear(){
        customItems.clear();
        creatableItemsCache = null;
    }

    private List<String> creatableItemsCache;
    public List<String> getCreatableItems() {
        if(creatableItemsCache == null)
            this.creatableItemsCache = customItems.entrySet().stream().filter(e -> !(e.getValue() instanceof IGUIItem)).map(e -> e.getKey()).collect(Collectors.toUnmodifiableList());
        return creatableItemsCache;
    }
}
