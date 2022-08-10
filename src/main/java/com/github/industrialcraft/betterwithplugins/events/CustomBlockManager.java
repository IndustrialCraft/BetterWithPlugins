package com.github.industrialcraft.betterwithplugins.events;

import com.github.industrialcraft.betterwithplugins.BWPMain;
import com.github.industrialcraft.betterwithplugins.block.CustomBlock;
import com.github.industrialcraft.betterwithplugins.items.CustomItem;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class CustomBlockManager implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent event){
        CustomBlock customBlock = BWPMain.getBlockRegistry().fromState(event.getBlock().getState());
        if(customBlock != null)
            customBlock.onBreak(event);
    }
    @EventHandler
    public void onExplode(BlockExplodeEvent event){
        event.blockList().removeIf(block -> {
            CustomBlock customBlock = BWPMain.getBlockRegistry().fromState(block.getState());
            if(customBlock != null) {
                if(!customBlock.onExplode(block.getLocation(), event))
                    return true;
            }
            return false;
        });
    }
    @EventHandler
    public void onUse(PlayerInteractEvent event){
        if(!event.hasBlock())
            return;
        CustomBlock customBlock = BWPMain.getBlockRegistry().fromState(event.getClickedBlock().getState());
        if(customBlock != null)
            customBlock.onUse(event);
    }
}
