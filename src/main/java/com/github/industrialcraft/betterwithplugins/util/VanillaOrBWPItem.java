package com.github.industrialcraft.betterwithplugins.util;

import com.github.industrialcraft.betterwithplugins.BWPMain;
import com.github.industrialcraft.betterwithplugins.items.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.List;

public class VanillaOrBWPItem {
    public final Material vanilla;
    public final CustomItem bwp;
    public VanillaOrBWPItem(Material vanilla) {
        this.vanilla = vanilla;
        this.bwp = null;
    }
    public VanillaOrBWPItem(CustomItem bwp) {
        this.vanilla = null;
        this.bwp = bwp;
    }
    public static VanillaOrBWPItem fromName(String name){
        Material material = Material.getMaterial(name);
        if(material != null)
            return new VanillaOrBWPItem(material);
        CustomItem customItem = BWPMain.getItemRegistry().fromId(name);
        if(customItem != null)
            return new VanillaOrBWPItem(customItem);
        return null;
    }
    public ItemStack create(int count){
        if(vanilla != null)
            return new ItemStack(vanilla, count);
        if(bwp != null)
            return bwp.create(count);
        return null;
    }
    public ItemStack create(){
        return create(1);
    }
    @Override
    public String toString() {
        if(vanilla != null)
            return vanilla.toString();
        if(bwp != null)
            return bwp.getName();
        return "";
    }
    public List<Recipe> getRecipes(){   //todo: cache
        return Bukkit.getRecipesFor(create());
    }
    public boolean hasRecipes(){
        return getRecipes().size() > 0;
    }
}
