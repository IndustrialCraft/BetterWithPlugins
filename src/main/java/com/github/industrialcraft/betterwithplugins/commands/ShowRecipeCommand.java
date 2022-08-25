package com.github.industrialcraft.betterwithplugins.commands;

import com.github.industrialcraft.betterwithplugins.BWPMain;
import com.github.industrialcraft.betterwithplugins.energy.IItemEnergyStorage;
import com.github.industrialcraft.betterwithplugins.items.CustomItem;
import com.github.industrialcraft.betterwithplugins.recipeviewer.RecipeViewerGUI;
import com.github.industrialcraft.betterwithplugins.util.VanillaOrBWPItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ShowRecipeCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1)
            return false;
        if(sender instanceof Player player) {
            if(!player.isOp()){
                player.sendMessage("Op is required to run this command");
                return true;
            }
            VanillaOrBWPItem item = VanillaOrBWPItem.fromName(args[0]);
            if(item == null){
                sender.sendMessage("Item not found");
                return true;
            }
            player.openInventory(RecipeViewerGUI.GUI.create(new RecipeViewerGUI.CurrentRecipeData(item)));
        } else {
            sender.sendMessage("Only player can run this command");
        }
        return true;
    }

    private static final List EMPTY = new ArrayList();
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return EMPTY;
    }

    public void register(String commandName){
        PluginCommand command = Bukkit.getServer().getPluginCommand(commandName);
        command.setExecutor(this);
        command.setTabCompleter(this);
    }
}
