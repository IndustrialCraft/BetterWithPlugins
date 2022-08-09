package com.github.industrialcraft.betterwithplugins.test;

import com.github.industrialcraft.betterwithplugins.BWPMain;
import com.github.industrialcraft.betterwithplugins.items.BasicCustomItem;
import com.github.industrialcraft.betterwithplugins.items.CustomItem;
import com.github.industrialcraft.betterwithplugins.items.handler.IEatHandler;
import com.github.industrialcraft.betterwithplugins.items.handler.IUseHandler;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Test {
    private final CustomItem CUSTOM_STICK;
    private final CustomItem CUSTOM_APPLE;
    public Test(){
        this.CUSTOM_STICK = new CustomStickItem(BWPMain.getInstance().createKey("custom_stick"), Material.STICK, "Custom Stick").register();
        this.CUSTOM_APPLE = new CustomAppleItem(BWPMain.getInstance().createKey("custom_apple"), Material.APPLE, "Custom Apple").register();
    }
    public static class CustomAppleItem extends BasicCustomItem implements IEatHandler {
        public CustomAppleItem(NamespacedKey key, Material material, String name, String... lore) {
            super(key, material, name, lore);
        }
        @Override
        public void onEat(PlayerItemConsumeEvent event) {
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 5, 0));
        }
    }
    public static class CustomStickItem extends BasicCustomItem implements IUseHandler {
        public CustomStickItem(NamespacedKey key, Material material, String name, String... lore) {
            super(key, material, name, lore);
        }
        @Override
        public void onItemUse(PlayerInteractEvent event) {
            event.getPlayer().getWorld().strikeLightning(event.getClickedBlock().getRelative(event.getBlockFace()).getLocation());
        }
    }
}
