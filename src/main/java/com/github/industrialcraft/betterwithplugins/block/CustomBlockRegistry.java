package com.github.industrialcraft.betterwithplugins.block;

import com.github.industrialcraft.betterwithplugins.BWPMain;
import com.github.industrialcraft.betterwithplugins.items.CustomItem;
import com.github.industrialcraft.betterwithplugins.items.IGUIItem;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CustomBlockRegistry {
    private HashMap<String, CustomBlock> customBlocks;
    public CustomBlockRegistry(){
        this.customBlocks = new HashMap<>();
        this.creatableBlocksCache = null;
    }
    public void register(CustomBlock customBlock){
        String id = customBlock.getKey().toString();
        if(customBlocks.containsKey(id))
            throw new IllegalStateException("Custom block " + id + " is already registered.");
        this.customBlocks.put(id, customBlock);
        this.creatableBlocksCache = null;
    }
    public CustomBlock fromState(BlockState state){
        String id = CustomBlock.getCustomBlockId(state);
        return customBlocks.get(id);
    }
    public CustomBlock fromId(String id){
        return customBlocks.get(id);
    }
    public void clear(){
        customBlocks.clear();
        creatableBlocksCache = null;
    }

    private List<String> creatableBlocksCache;
    public List<String> getCreatableBlocks() {
        if(creatableBlocksCache == null)
            this.creatableBlocksCache = customBlocks.entrySet().stream().map(e -> e.getKey()).collect(Collectors.toUnmodifiableList());
        return creatableBlocksCache;
    }
}
