package com.github.industrialcraft.betterwithplugins.items.handler;

import org.bukkit.event.player.PlayerItemConsumeEvent;

public interface IEatHandler {
    void onEat(PlayerItemConsumeEvent event);
}
