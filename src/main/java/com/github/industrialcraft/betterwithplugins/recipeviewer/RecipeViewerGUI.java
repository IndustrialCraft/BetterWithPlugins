package com.github.industrialcraft.betterwithplugins.recipeviewer;

import com.github.industrialcraft.betterwithplugins.BWPMain;
import com.github.industrialcraft.betterwithplugins.gui.GUI;
import com.github.industrialcraft.betterwithplugins.items.CustomItem;
import com.github.industrialcraft.betterwithplugins.util.VanillaOrBWPItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.function.Consumer;

public class RecipeViewerGUI extends GUI<RecipeViewerGUI.CurrentRecipeData> {
    public static final RecipeViewerGUI GUI = new RecipeViewerGUI();
    public RecipeViewerGUI() {
        super((inventory) -> {
            InventoryDataHolder<CurrentRecipeData> dataHolder = (InventoryDataHolder) inventory.getHolder();
            reloadContent(inventory, dataHolder.data);
        });
    }
    public static void reloadContent(Inventory inventory, CurrentRecipeData recipeData){
        InventoryDataHolder<CurrentRecipeData> dataHolder = (InventoryDataHolder) inventory.getHolder();
        dataHolder.data = recipeData;
        if(recipeData.page == -1)
            return;
        Recipe recipe = recipeData.item.getRecipes().get(recipeData.page);
        inventory.setItem(0, recipe.getResult());
        VanillaRecipeViewer.fillContent(inventory, recipe, dataHolder.data.getChoiceCycle());
    }
    @Override
    public boolean onClick(InventoryClickEvent event, CustomItem currentCustomItem, CustomItem cursorCustomItem) {
        InventoryDataHolder<CurrentRecipeData> dataHolder = (InventoryDataHolder) event.getClickedInventory().getHolder();
        if(event.getClick() == ClickType.DROP){
            dataHolder.data.nextChoiceCycle();
            reloadContent(event.getClickedInventory(), dataHolder.data);
            return true;
        }
        if (event.getSlot() == 0) {
            reloadContent(event.getClickedInventory(), dataHolder.data.previous());
        }
        if (event.getSlot() == 1) {
            reloadContent(event.getClickedInventory(), dataHolder.data.next());
        }
        return true;
    }
    @Override
    public boolean onDrag(InventoryDragEvent event, CustomItem cursorCustomItem) {
        return false;
    }

    @Override
    protected Inventory createEmpty(InventoryDataHolder<CurrentRecipeData> dataHolder) {
        return Bukkit.createInventory(dataHolder, InventoryType.CHEST, "Recipe viewer");
    }

    public static class CurrentRecipeData {
        private VanillaOrBWPItem item;
        private int page;
        private int choiceCycler;
        public CurrentRecipeData(VanillaOrBWPItem item) {
            this.item = item;
            setPage(0);
            this.choiceCycler = 0;
        }
        public CurrentRecipeData previous(){
            setPage(getPage()-1);
            return this;
        }
        public CurrentRecipeData next(){
            setPage(getPage()+1);
            return this;
        }
        public int getChoiceCycle() {
            return choiceCycler;
        }
        public void nextChoiceCycle(){
            this.choiceCycler++;
        }
        public VanillaOrBWPItem getItem() {
            return item;
        }
        public void setItem(VanillaOrBWPItem item) {
            this.choiceCycler = 0;
            if(!item.hasRecipes())
                return;
            this.item = item;
            setPage(0);
        }
        public int getPage() {
            return page;
        }
        public void setPage(int page) {
            this.choiceCycler = 0;
            int recipeLength = this.item.getRecipes().size();
            if(page >= recipeLength){
                this.page = recipeLength-1;
                return;
            }
            if(page < 0) {
                this.page = recipeLength==0?-1:0;
                return;
            }
            this.page = page;
        }

        @Override
        public String toString() {
            return item + " " + page;
        }
    }
}
