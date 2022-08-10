package com.github.industrialcraft.betterwithplugins.test;

import com.github.industrialcraft.betterwithplugins.BWPMain;
import com.github.industrialcraft.betterwithplugins.block.CustomBlock;
import com.github.industrialcraft.betterwithplugins.items.BasicCustomItem;
import com.github.industrialcraft.betterwithplugins.items.CustomItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.*;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Test {
    public final CustomItem CUSTOM_STICK;
    public final CustomItem CUSTOM_APPLE;
    public final CustomBlock CUSTOM_DISPLAYER;
    public Test(){
        this.CUSTOM_STICK = new BasicCustomItem(BWPMain.getInstance().createKey("custom_stick"), Material.STICK, "Custom Stick"){
            @Override
            public void onItemUse(PlayerInteractEvent event) {
                event.getPlayer().getWorld().strikeLightning(event.getClickedBlock().getRelative(event.getBlockFace()).getLocation());
            }
        }.register();

        this.CUSTOM_APPLE = new BasicCustomItem(BWPMain.getInstance().createKey("custom_apple"), Material.APPLE, "Custom Apple"){
            @Override
            public void onEat(PlayerItemConsumeEvent event) {
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 5, 0));
            }
        }.register();

        this.CUSTOM_DISPLAYER = new CustomBlock(BWPMain.getInstance().createKey("custom_displayer"), Material.DROPPER){
            @Override
            public void onBreak(BlockBreakEvent event) {
                event.setDropItems(false);
            }

            @Override
            public void onUse(PlayerInteractEvent event) {
                event.getPlayer().sendMessage("displayer used");
            }

            @Override
            public TileState setAt(Location location) {
                TileState state = super.setAt(location);
                Dispenser dispenser = ((Dispenser)state.getBlockData());
                dispenser.setFacing(BlockFace.UP);
                state.setBlockData(dispenser);
                state.update(true);
                return state;
            }
        }.register();
    }
}
