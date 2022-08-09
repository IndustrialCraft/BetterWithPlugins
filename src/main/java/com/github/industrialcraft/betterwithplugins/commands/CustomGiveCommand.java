package com.github.industrialcraft.betterwithplugins.commands;

import com.github.industrialcraft.betterwithplugins.BWPMain;
import com.github.industrialcraft.betterwithplugins.items.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomGiveCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1 || args.length > 2)
            return false;
        if(sender instanceof Player player) {
            if(!player.isOp()){
                player.sendMessage("Op is required to run this command");
                return true;
            }
            CustomItem customItem = BWPMain.getItemRegistry().fromId(args[0]);
            if (customItem == null) {
                player.sendMessage("Unknown item: " + args[0]);
                return true;
            }
            int count = 1;
            if(args.length == 2) {
                try {
                    count = Integer.parseInt(args[1]);
                } catch (Exception e){
                    player.sendMessage(args[1] + " is not a number");
                    return true;
                }
            }
            player.getInventory().addItem(customItem.create(count));
        } else {
            sender.sendMessage("Only player can run this command");
        }
        return true;
    }

    private static final List EMPTY = new ArrayList();
    private static final List INVALID_ITEM = Arrays.asList("<Invalid Item>");
    private static final List ITEM_COUNT_ONE = Arrays.asList("1");
    private static final List ITEM_COUNT_SIXTEEN = Arrays.asList("1", "4", "8", "16");
    private static final List ITEM_COUNT_SIXTY_FOUR = Arrays.asList("1", "16", "32", "64");
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {
            if (args[0].isEmpty())
                return BWPMain.getItemRegistry().getCreatableItems();
            return BWPMain.getItemRegistry().getCreatableItems().stream().filter(s -> s.contains(args[0])).collect(Collectors.toUnmodifiableList());
        }
        if(args.length == 2){
            CustomItem item = BWPMain.getItemRegistry().fromId(args[0]);
            if(item == null)
                return INVALID_ITEM;
            int maxStackSize = item.getMaxStackSize();
            if(maxStackSize == 64)
                return ITEM_COUNT_SIXTY_FOUR;
            if(maxStackSize == 16)
                return ITEM_COUNT_SIXTEEN;
            return ITEM_COUNT_ONE;
        }
        return EMPTY;
    }

    public void register(String commandName){
        PluginCommand command = Bukkit.getServer().getPluginCommand(commandName);
        command.setExecutor(this);
        command.setTabCompleter(this);
    }
}
