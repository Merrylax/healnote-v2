package fr.merrylax.healnote.recipes;

import fr.merrylax.healnote.HealNoteV2;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.RecipeChoice;
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
        ShapedRecipe recipe = new ShapedRecipe(key, plugin.getItemManager().createMauditePages());
        recipe.shape(
            "NSN",
            "SPS",
            "NSN"
        );
        recipe.setIngredient('N', Material.NETHER_STAR);
        recipe.setIngredient('S', Material.PIGLIN_HEAD);
        recipe.setIngredient('P', Material.PAPER);
        plugin.getServer().addRecipe(recipe);
    }

    private void registerDeathNoteRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, "death_note");
        ShapedRecipe recipe = new ShapedRecipe(key, plugin.getItemManager().createDeathNote());
        recipe.shape(
            "TMT",
            "MBM",
            "TMT"
        );
        recipe.setIngredient('T', Material.TOTEM_OF_UNDYING);
        recipe.setIngredient('M', Material.PAPER); // Page Maudite (affichage custom du PAPER)
        recipe.setIngredient('B', Material.WRITABLE_BOOK);
        plugin.getServer().addRecipe(recipe);
    }

    private void registerHealNoteRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, "heal_note");
        ShapedRecipe recipe = new ShapedRecipe(key, plugin.getItemManager().createHealNote());
        recipe.shape(
            "TMT",
            "MBM",
            "TMT"
        );
        recipe.setIngredient('T', Material.TOTEM_OF_UNDYING);
        recipe.setIngredient('M', Material.PAPER); // Page Maudite (affichage custom du PAPER)
        recipe.setIngredient('B', Material.WRITABLE_BOOK);
        plugin.getServer().addRecipe(recipe);
    }

    private void registerDebanBookRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, "deban_book");
        ShapedRecipe recipe = new ShapedRecipe(key, plugin.getItemManager().createDebanBook());
        recipe.shape(
            "NSN",
            "SBN",
            "NSN"
        );
        recipe.setIngredient('N', Material.NETHER_STAR);
        recipe.setIngredient('S', Material.PIGLIN_HEAD);
        recipe.setIngredient('B', Material.WRITABLE_BOOK);
        plugin.getServer().addRecipe(recipe);
    }
}
