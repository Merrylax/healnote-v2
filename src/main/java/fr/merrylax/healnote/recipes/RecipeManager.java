package fr.merrylax.healnote.recipes;

import fr.merrylax.healnote.HealNoteV2;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeManager {

    private final HealNoteV2 plugin;

    public RecipeManager(HealNoteV2 plugin) {
        this.plugin = plugin;
    }

    public void registerRecipes() {
        registerMauditePageRecipe();
        registerDeathNoteRecipe();
        registerHealNoteRecipe();
        registerDebanBookRecipe();
    }

    private void registerMauditePageRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, "maudite_page");
        ShapedRecipe recipe = new ShapedRecipe(key, plugin.getItemManager().createMauditeePage());
        recipe.shape("NND", "NDN", "DND");
        recipe.setIngredient('N', Material.NETHER_STAR);
        recipe.setIngredient('D', Material.REINFORCED_DEEPSLATE);
        plugin.getServer().addRecipe(recipe);
    }

    private void registerDeathNoteRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, "death_note");
        ShapedRecipe recipe = new ShapedRecipe(key, plugin.getItemManager().createDeathNote());
        recipe.shape("PPP", "PEP", "PPP");
        recipe.setIngredient('P', Material.PAPER);
        recipe.setIngredient('E', Material.ENCHANTING_TABLE);
        plugin.getServer().addRecipe(recipe);
    }

    private void registerHealNoteRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, "heal_note");
        ShapedRecipe recipe = new ShapedRecipe(key, plugin.getItemManager().createHealNote());
        recipe.shape("GGG", "GEG", "GGG");
        recipe.setIngredient('G', Material.GOLD_BLOCK);
        recipe.setIngredient('E', Material.ENCHANTING_TABLE);
        plugin.getServer().addRecipe(recipe);
    }

    private void registerDebanBookRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, "deban_book");
        ShapedRecipe recipe = new ShapedRecipe(key, plugin.getItemManager().createDebanBook());
        recipe.shape("DDD", "DED", "DDD");
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('E', Material.ENCHANTING_TABLE);
        plugin.getServer().addRecipe(recipe);
    }
}