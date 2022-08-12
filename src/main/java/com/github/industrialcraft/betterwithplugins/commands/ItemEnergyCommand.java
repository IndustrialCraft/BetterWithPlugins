package com.github.industrialcraft.betterwithplugins.commands;

import com.github.industrialcraft.betterwithplugins.BWPMain;
import com.github.industrialcraft.betterwithplugins.energy.IItemEnergyStorage;
import com.github.industrialcraft.betterwithplugins.items.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemEnergyCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2)
            return false;
        if(sender instanceof Player player) {
            if(!player.isOp()){
                player.sendMessage("Op is required to run this command");
                return true;
            }
            int amount = 0;
            try{
                amount = Integer.parseInt(args[1]);
            } catch (Exception e){
                player.sendMessage("Energy count must be number");
                return true;
            }
            ItemStack item = player.getInventory().getItemInMainHand();
            CustomItem customItem = BWPMain.getItemRegistry().fromStack(item);
            if(customItem instanceof IItemEnergyStorage itemEnergyStorage) {
                String type = args[0];
                switch (type) {
                    case "SET":
                        itemEnergyStorage.setStoredEnergy(item, amount);
                        break;
                    case "CHARGE":
                        itemEnergyStorage.chargeEnergy(item, amount);
                        break;
                    case "TAKE_MAX":
                        itemEnergyStorage.takeEnergy(item, amount);
                        break;
                    case "TAKE_IF_HAS":
                        itemEnergyStorage.takeIfEnough(item, amount);
                        break;
                    default:
                        player.sendMessage("Unknown mode");
                        break;
                }
            } else {
                sender.sendMessage("Item in hand is not chargeable");
            }
        } else {
            sender.sendMessage("Only player can run this command");
        }
        return true;
    }

    private static final List EMPTY = new ArrayList();
    private static final List<String> TYPE = Arrays.asList("SET", "CHARGE", "TAKE_MAX", "TAKE_IF_HAS");
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {
            if (args[0].isEmpty())
                return TYPE;
            return TYPE.stream().filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase())).collect(Collectors.toUnmodifiableList());
        }
        return EMPTY;
    }

    public void register(String commandName){
        PluginCommand command = Bukkit.getServer().getPluginCommand(commandName);
        command.setExecutor(this);
        command.setTabCompleter(this);
    }
}
