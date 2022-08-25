package com.github.industrialcraft.betterwithplugins.recipeviewer;

import org.bukkit.Material;
import org.bukkit.inventory.*;

import java.text.ChoiceFormat;

public class VanillaRecipeViewer {
    public static void fillContent(Inventory inventory, Recipe recipe, int choiceCycler){
        if (recipe instanceof FurnaceRecipe furnaceRecipe) {
            inventory.setItem(1, new ItemStack(Material.FURNACE));
            inventory.setItem(2, fromChoice(furnaceRecipe.getInputChoice(), choiceCycler));
        }
        if (recipe instanceof SmokingRecipe smokingRecipe) {
            inventory.setItem(1, new ItemStack(Material.SMOKER));
            inventory.setItem(2, fromChoice(smokingRecipe.getInputChoice(), choiceCycler));
        }
        if (recipe instanceof BlastingRecipe blastingRecipe) {
            inventory.setItem(1, new ItemStack(Material.BLAST_FURNACE));
            inventory.setItem(2, fromChoice(blastingRecipe.getInputChoice(), choiceCycler));
        }
        if (recipe instanceof CampfireRecipe campfireRecipe) {
            inventory.setItem(1, new ItemStack(Material.CAMPFIRE));
            inventory.setItem(2, fromChoice(campfireRecipe.getInputChoice(), choiceCycler));
        }
        if (recipe instanceof SmithingRecipe smithingRecipe) {
            inventory.setItem(1, new ItemStack(Material.SMITHING_TABLE));
            inventory.setItem(2, fromChoice(smithingRecipe.getBase(), choiceCycler));
            inventory.setItem(3, fromChoice(smithingRecipe.getAddition(), choiceCycler));
        }
        if (recipe instanceof StonecuttingRecipe stonecuttingRecipe) {
            inventory.setItem(1, new ItemStack(Material.STONECUTTER));
            inventory.setItem(2, fromChoice(stonecuttingRecipe.getInputChoice(), choiceCycler));
        }
    }
    public static ItemStack fromChoice(RecipeChoice choice, int cycle){
        if(choice instanceof RecipeChoice.ExactChoice exactChoice){
            int choices = exactChoice.getChoices().size();
            return exactChoice.getChoices().get(cycle % choices);
        }
        if(choice instanceof RecipeChoice.MaterialChoice materialChoice){
            int choices = materialChoice.getChoices().size();
            return new ItemStack(materialChoice.getChoices().get(cycle % choices));
        }
        return null;
    }
}
