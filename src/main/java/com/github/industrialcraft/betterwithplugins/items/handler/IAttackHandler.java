package com.github.industrialcraft.betterwithplugins.items.handler;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public interface IAttackHandler {
    void onAttack(EntityDamageByEntityEvent event, Player player);
}
