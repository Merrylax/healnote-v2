package fr.merrylax.healnote.items;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Arrays;
import java.util.UUID;

public class ItemManager {

    private final JavaPlugin plugin;
    private final NamespacedKey bookIdKey;
    private final NamespacedKey bookTypeKey;

    public ItemManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.bookIdKey = new NamespacedKey(plugin, "book_id");
        this.bookTypeKey = new NamespacedKey(plugin, "book_type");
    }

    public ItemStack createMauditePages() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6📜 Page Maudite");
            meta.setLore(Arrays.asList(
                "§7Ingrédient spécial pour les items magiques.",
                "§c§lNécessaire pour crafter Death Note & Heal Note"
            ));
            item.setItemMeta(meta);
        }
        return item;
    }

    public ItemStack createDeathNote() {
        ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§c☠️ Death Note");
            meta.setLore(Arrays.asList(
                "§7Clic droit pour utiliser",
                "§8",
                "§c➤ Écrivez le pseudo de la victime",
                "§c➤ Écrivez le temps (3-3600 secondes)",
                "§c➤ Appuyez sur Entrée pour valider",
                "§8",
                "§c§lLE SORT EST IRRÉVOCABLE"
            ));
            meta.addEnchant(Enchantment.UNBREAKING, 3, true);
            
            // Ajouter PersistentDataContainer
            PersistentDataContainer container = meta.getPersistentDataContainer();
            String uniqueId = UUID.randomUUID().toString();
            container.set(bookIdKey, PersistentDataType.STRING, uniqueId);
            container.set(bookTypeKey, PersistentDataType.STRING, "death_note");
            
            item.setItemMeta(meta);
        }
        return item;
    }

    public ItemStack createHealNote() {
        ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§a💚 Heal Note");
            meta.setLore(Arrays.asList(
                "§7Clic droit pour utiliser",
                "§8",
                "§a➤ Écrivez le pseudo du joueur condamné",
                "§a➤ Écrivez le temps du sacrifice (3-3600)",
                "§a➤ Appuyez sur Entrée pour valider",
                "§8",
                "§a§lLE SACRIFICE EST ULTIME"
            ));
            meta.addEnchant(Enchantment.UNBREAKING, 3, true);
            
            // Ajouter PersistentDataContainer
            PersistentDataContainer container = meta.getPersistentDataContainer();
            String uniqueId = UUID.randomUUID().toString();
            container.set(bookIdKey, PersistentDataType.STRING, uniqueId);
            container.set(bookTypeKey, PersistentDataType.STRING, "heal_note");
            
            item.setItemMeta(meta);
        }
        return item;
    }

    public ItemStack createDebanBook() {
        ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6📖 Deban Book");
            meta.setLore(Arrays.asList(
                "§7Clic droit pour utiliser",
                "§8",
                "§6➤ Écrivez le pseudo du joueur banni",
                "§6➤ Écrivez le temps du défi (3-3600)",
                "§6➤ Appuyez sur Entrée pour valider",
                "§8",
                "§6§lLE DESTIN DÉCIDE"
            ));
            
            // Ajouter PersistentDataContainer
            PersistentDataContainer container = meta.getPersistentDataContainer();
            String uniqueId = UUID.randomUUID().toString();
            container.set(bookIdKey, PersistentDataType.STRING, uniqueId);
            container.set(bookTypeKey, PersistentDataType.STRING, "deban_book");
            
            item.setItemMeta(meta);
        }
        return item;
    }

    public String getBookId(ItemStack item) {
        if (item == null || item.getItemMeta() == null) return null;
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        return container.get(bookIdKey, PersistentDataType.STRING);
    }

    public String getBookType(ItemStack item) {
        if (item == null || item.getItemMeta() == null) return null;
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        return container.get(bookTypeKey, PersistentDataType.STRING);
    }

    public NamespacedKey getBookIdKey() {
        return bookIdKey;
    }

    public NamespacedKey getBookTypeKey() {
        return bookTypeKey;
    }
}