package com.github.industrialcraft.betterwithplugins.items.handler;

import org.bukkit.event.player.PlayerInteractEntityEvent;

public interface IEntityUseHandler {
    void onEntityUse(PlayerInteractEntityEvent event);
}
