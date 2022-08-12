package com.github.industrialcraft.betterwithplugins;

import com.github.industrialcraft.betterwithplugins.block.BlockTicker;
import com.github.industrialcraft.betterwithplugins.events.CustomBlockManager;
import com.github.industrialcraft.betterwithplugins.block.CustomBlockRegistry;
import com.github.industrialcraft.betterwithplugins.commands.CustomGiveCommand;
import com.github.industrialcraft.betterwithplugins.commands.CustomSetblockCommand;
import com.github.industrialcraft.betterwithplugins.events.GUIManager;
import com.github.industrialcraft.betterwithplugins.events.CustomItemManager;
import com.github.industrialcraft.betterwithplugins.events.GuiItemMoveCanceller;
import com.github.industrialcraft.betterwithplugins.items.CustomItemRegistry;
import com.github.industrialcraft.betterwithplugins.items.CustomModelDataAssigner;
import com.github.industrialcraft.betterwithplugins.test.Test;
import com.github.industrialcraft.betterwithplugins.util.Keys;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class BWPMain extends JavaPlugin {
    private Keys keys;
    private CustomItemRegistry itemRegistry;
    private CustomBlockRegistry blockRegistry;
    private CustomModelDataAssigner customModelDataAssigner;
    private BlockTicker blockTicker;
    @Override
    public void onEnable() {
        this.keys = new Keys(this);
        this.customModelDataAssigner = new CustomModelDataAssigner();
        this.customModelDataAssigner.load(getConfig());
        this.itemRegistry = new CustomItemRegistry();
        this.blockRegistry = new CustomBlockRegistry();
        this.blockTicker = new BlockTicker();
        this.blockTicker.initTicking(this, 5);
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new GUIManager(), this);
        pm.registerEvents(new CustomItemManager(), this);
        pm.registerEvents(new CustomBlockManager(), this);
        pm.registerEvents(new GuiItemMoveCanceller(), this);
        pm.registerEvents(this.blockTicker, this);
        new CustomGiveCommand().register("bwpgive");
        new CustomSetblockCommand().register("bwpsetblock");
        new Test();
    }

    @Override
    public void onDisable() {
        itemRegistry.clear();
        this.customModelDataAssigner.save(getConfig());
    }
    public static Keys getKeys(){
        return getInstance().keys;
    }
    public static CustomItemRegistry getItemRegistry(){
        return getInstance().itemRegistry;
    }
    public static CustomBlockRegistry getBlockRegistry(){
        return getInstance().blockRegistry;
    }
    public static BWPMain getInstance(){
        return JavaPlugin.getPlugin(BWPMain.class);
    }
    public NamespacedKey createKey(String key){
        return new NamespacedKey(this, key);
    }
    public static CustomModelDataAssigner getCustomModelDataAssigner(){
        return getInstance().customModelDataAssigner;
    }
    public static BlockTicker getBlockTicker(){
        return getInstance().blockTicker;
    }
}
