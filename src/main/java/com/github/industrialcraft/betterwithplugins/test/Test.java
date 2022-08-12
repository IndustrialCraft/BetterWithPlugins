package com.github.industrialcraft.betterwithplugins.test;

import com.github.industrialcraft.betterwithplugins.BWPMain;
import com.github.industrialcraft.betterwithplugins.block.BasicTickingBlock;
import com.github.industrialcraft.betterwithplugins.block.CustomBlock;
import com.github.industrialcraft.betterwithplugins.energy.EnergyStoringItem;
import com.github.industrialcraft.betterwithplugins.gui.GUI;
import com.github.industrialcraft.betterwithplugins.items.BasicCustomItem;
import com.github.industrialcraft.betterwithplugins.items.BasicGuiItem;
import com.github.industrialcraft.betterwithplugins.items.CustomItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.*;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.function.Consumer;

public class Test {
    public final CustomItem CUSTOM_STICK;
    public final CustomItem CUSTOM_APPLE;
    public final CustomBlock CUSTOM_DISPLAYER;
    public final CustomBlock CUSTOM_TICKER;
    public final GUI CUSTOM_GUI;
    public final CustomItem GUI_FILLER;
    public final CustomItem CUSTOM_BATTERY;
    public Test(){
        this.CUSTOM_STICK = new BasicCustomItem(BWPMain.getInstance().createKey("custom_stick"), Material.STICK, "Custom Stick"){
            @Override
            public void onItemUse(PlayerInteractEvent event) {
                if(event.hasBlock())
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
            public boolean onExplode(Location location, BlockExplodeEvent event) {
                location.getWorld().setType(location, Material.AIR);
                return false;
            }

            @Override
            public void onUse(PlayerInteractEvent event) {
                event.getPlayer().sendMessage("displayer used");
                event.getPlayer().openInventory(Test.this.CUSTOM_GUI.create(null));
                event.setCancelled(true);
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
        this.CUSTOM_TICKER = new BasicTickingBlock(BWPMain.getInstance().createKey("custom_ticker"), Material.BELL){
            @Override
            public void tick(Block block) {
                block.getLocation().getWorld().spawnParticle(Particle.ELECTRIC_SPARK, block.getLocation().add(0, 1, 0), 5);
            }
        }.register();
        //this.CUSTOM_GUI = new GUI("Custom GUI", InventoryType.DROPPER, (inventory) -> inventory.addItem(Test.this.GUI_FILLER.create(8)));
        this.CUSTOM_GUI = new GUI("Custom GUI", InventoryType.DROPPER, new Consumer<Inventory>() {
            @Override
            public void accept(Inventory itemStacks) {
                itemStacks.addItem(Test.this.GUI_FILLER.create(8));
            }
        });
        this.GUI_FILLER = new BasicGuiItem(BWPMain.getInstance().createKey("gui_filler")).register();
        this.CUSTOM_BATTERY = new EnergyStoringItem(BWPMain.getInstance().createKey("custom_battery"), 1024, Material.STICK, "Custom Battery", "0/1024").register();
    }
}
