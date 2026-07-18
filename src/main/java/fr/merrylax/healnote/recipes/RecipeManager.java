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
        ShapedRecipe recipe = new ShapedRecipe(key, plugin.getItemManager().createMauditePage());
        recipe.shape(
            "NSN",
            "SPS",
            "NSN"
    );

    recipe.setIngredient('N', Material.NETHER_STAR);
    recipe.setIngredient('S', Material.REINFORCED_DEEPSLATE);
    recipe.setIngredient('P', Material.PAPER); 
        plugin.getServer().addRecipe(recipe);
    }

    private void registerDeathNoteRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, "death_note");
        ShapedRecipe recipe = new ShapedRecipe(key, plugin.getItemManager().createDeathNote());
        recipe.shape(
            "TNT",
            "NBN",
            "TNT"
    );

    recipe.setIngredient('T', Material.TOTEM_OF_UNDYING);
    recipe.setIngredient('N', Material.PAPER);
    recipe.setIngredient('B', Material.WRITABLE_BOOK);
        plugin.getServer().addRecipe(recipe);
    }

    private void registerHealNoteRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, "heal_note");
        ShapedRecipe recipe = new ShapedRecipe(key, plugin.getItemManager().createHealNote());
         recipe.shape(
            "NTN",
            "TBT",
            "NTN"
    );

    recipe.setIngredient('N', Material.PAPER);
    recipe.setIngredient('T', Material.TOTEM_OF_UNDYING);
    recipe.setIngredient('B', Material.WRITABLE_BOOK);
        plugin.getServer().addRecipe(recipe);
    }

    private void registerDebanBookRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, "deban_book");
        ShapedRecipe recipe = new ShapedRecipe(key, plugin.getItemManager().createDebanBook());
       recipe.shape(
            "NSN",
            "SBS",
            "NSN"
    );

    recipe.setIngredient('N', Material.NETHER_STAR);
    recipe.setIngredient('S', Material.REINFORCED_DEEPSLATE);
    recipe.setIngredient('B', Material.WRITABLE_BOOK);
        plugin.getServer().addRecipe(recipe);
    }
}
