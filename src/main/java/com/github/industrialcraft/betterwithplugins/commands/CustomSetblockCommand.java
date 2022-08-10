package com.github.industrialcraft.betterwithplugins.commands;

import com.github.industrialcraft.betterwithplugins.BWPMain;
import com.github.industrialcraft.betterwithplugins.block.CustomBlock;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomSetblockCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1)
            return false;
        if(sender instanceof Player player) {
            if(!player.isOp()){
                player.sendMessage("Op is required to run this command");
                return true;
            }
            CustomBlock customBlock = BWPMain.getBlockRegistry().fromId(args[0]);
            if (customBlock == null) {
                player.sendMessage("Unknown block: " + args[0]);
                return true;
            }
            customBlock.setAt(player.getLocation());
        } else {
            sender.sendMessage("Only player can run this command");
        }
        return true;
    }

    private static final List EMPTY = new ArrayList();
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {
            if (args[0].isEmpty())
                return BWPMain.getBlockRegistry().getCreatableBlocks();
            return BWPMain.getBlockRegistry().getCreatableBlocks().stream().filter(s -> s.contains(args[0])).collect(Collectors.toUnmodifiableList());
        }
        return EMPTY;
    }

    public void register(String commandName){
        PluginCommand command = Bukkit.getServer().getPluginCommand(commandName);
        command.setExecutor(this);
        command.setTabCompleter(this);
    }
}
