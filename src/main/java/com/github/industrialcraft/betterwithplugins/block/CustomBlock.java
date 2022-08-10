package com.github.industrialcraft.betterwithplugins.block;

import com.github.industrialcraft.betterwithplugins.BWPMain;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

public class CustomBlock {
    private final NamespacedKey key;
    private final Material material;
    public CustomBlock(NamespacedKey key, Material material) {
        this.key = key;
        if(!material.isBlock())
            throw new IllegalArgumentException("material " + material.name() + " is not a block");
        //todo: check if material is tile entity
        this.material = material;
    }
    public TileState setAt(Location location){
        location.getWorld().setType(location, Material.AIR);    //todo: check if needed(remove previous tile entity data)
        location.getWorld().setType(location, material);
        BlockState state = location.getWorld().getBlockState(location);
        TileState tileState = (TileState) state;
        tileState.getPersistentDataContainer().set(BWPMain.getKeys().CUSTOM_ID_KEY, PersistentDataType.STRING, key.toString());
        tileState.update(true);
        return tileState;
    }
    public void onBreak(BlockBreakEvent event){}
    public boolean onExplode(Location location, BlockExplodeEvent event){return true;}
    public void onUse(PlayerInteractEvent event){}

    public CustomBlock register(){
        BWPMain.getBlockRegistry().register(this);
        return this;
    }

    public static String getCustomBlockId(BlockState state){
        if(state instanceof TileState tileState){
            return tileState.getPersistentDataContainer().get(BWPMain.getKeys().CUSTOM_ID_KEY, PersistentDataType.STRING);
        }
        return null;
    }

    public Material getMaterial() {
        return material;
    }
    public NamespacedKey getKey() {
        return key;
    }
}
