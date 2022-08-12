package com.github.industrialcraft.betterwithplugins.block;

import com.github.industrialcraft.betterwithplugins.BWPMain;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;

public class BlockTicker implements Listener {
    private HashSet<Location> tickingBlocks;
    public BlockTicker() {
        this.tickingBlocks = new HashSet<>();
    }
    public BukkitTask initTicking(JavaPlugin plugin, int period){
        return Bukkit.getScheduler().runTaskTimer(plugin, this::tick, period, period);
    }
    public void tick(){
        tickingBlocks.removeIf(location -> {
            if(!location.isWorldLoaded())
                return true;
            if(!location.getChunk().isLoaded())
                return true;
            CustomBlock customBlock = BWPMain.getBlockRegistry().fromState(location.getBlock().getState());
            if(!(customBlock instanceof ITickingBlock))
                return true;
            ((ITickingBlock)customBlock).tick(location.getBlock());
            return false;
        });
    }
    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event){
        for(BlockState state : event.getChunk().getTileEntities()){
            CustomBlock customBlock = BWPMain.getBlockRegistry().fromState(state);
            if(customBlock instanceof ITickingBlock)
                tickingBlocks.add(state.getLocation());
        }
    }
    public void addLocationToTick(Location location){
        this.tickingBlocks.add(location);
    }
}
