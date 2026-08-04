package fr.merrylax.healnote;

import fr.merrylax.healnote.listeners.*;
import fr.merrylax.healnote.items.ItemManager;
import fr.merrylax.healnote.managers.DeathManager;
import fr.merrylax.healnote.managers.BanManager;
import fr.merrylax.healnote.managers.GuardianManager;
import fr.merrylax.healnote.recipes.RecipeManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class HealNoteV2 extends JavaPlugin {

    private static HealNoteV2 instance;
    
    private ItemManager itemManager;
    private RecipeManager recipeManager;
    private DeathManager deathManager;
    private BanManager banManager;
    private GuardianManager guardianManager;
    
    private BookListener bookListener;
    private GuardianListener guardianListener;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        getLogger().info("═══════════════════════════════════════════════════════════════");
        getLogger().info("HealNote V2 - Démarrage...");
        getLogger().info("═══════════════════════════════════════════════════════════════");

        // Initialiser les managers
        itemManager = new ItemManager(this);
        recipeManager = new RecipeManager(this);
        deathManager = new DeathManager(this);
        banManager = new BanManager(this);
        guardianManager = new GuardianManager(this);

        // Enregistrer les recettes
        recipeManager.registerRecipes();

        // Démarrer les tâches
        deathManager.startTask();
        guardianManager.startTask();

        // Enregistrer les listeners
        bookListener = new BookListener(this);
        guardianListener = new GuardianListener(this);
        
        getServer().getPluginManager().registerEvents(bookListener, this);
        getServer().getPluginManager().registerEvents(guardianListener, this);

        getLogger().info("═══════════════════════════════════════════════════════════════");
        getLogger().info("✅ HealNote V2 v" + getDescription().getVersion() + " activé!");
        getLogger().info("Développeur: Merrylax");
        getLogger().info("═══════════════════════════════════════════════════════════════");
    }

    @Override
    public void onDisable() {
        getLogger().info("═══════════════════════════════════════════════════════════════");
        getLogger().info("❌ HealNote V2 désactivé.");
        getLogger().info("═══════════════════════════════════════════════════════════════");
    }

    public static HealNoteV2 getInstance() {
        return instance;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public RecipeManager getRecipeManager() {
        return recipeManager;
    }

    public DeathManager getDeathManager() {
        return deathManager;
    }

    public BanManager getBanManager() {
        return banManager;
    }

    public GuardianManager getGuardianManager() {
        return guardianManager;
    }
}
