package com.github.industrialcraft.betterwithplugins.items.handler;

import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public interface IUseHandler {
    void onItemUse(PlayerInteractEvent event);
}
