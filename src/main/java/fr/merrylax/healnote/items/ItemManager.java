package fr.merrylax.healnote.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Arrays;

public class ItemManager {

    private final JavaPlugin plugin;

    public ItemManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public ItemStack createMauditePage() {
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
                "§7Condamne un joueur à mort.",
                "§8",
                "§c➤ La nuit tombe immédiatement",
                "§c➤ Un orage éclate avec des éclairs",
                "§c➤ Tous les joueurs sont notifiés",
                "§c➤ Compte à rebours: 120 secondes",
                "§c➤ Les Totems d'immortalité sont IGNORÉS",
                "§8",
                "§c§lLE SORT EST IRRÉVOCABLE"
            ));
            meta.addEnchant(Enchantment.UNBREAKING, 3, true);
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
                "§7Sauvez un joueur condamné.",
                "§8",
                "§a➤ Le joueur condamné est sauvé",
                "§a➤ Mais VOUS mourez à sa place",
                "§a➤ Et VOUS serez banni définitivement",
                "§8",
                "§a§lLE SACRIFICE EST ULTIME"
            ));
            meta.addEnchant(Enchantment.UNBREAKING, 3, true);
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
                "§7Invoque le Gardien du Destin.",
                "§8",
                "§6➤ Défi de 45 MINUTES pour tout le serveur",
                "§6➤ Vaincre le Gardien = Débannir le joueur",
                "§6➤ Échouer = Le joueur reste banni à jamais",
                "§6➤ Le livre est consommé à l'utilisation",
                "§8",
                "§6§lLE DESTIN DÉCIDE"
            ));
            item.setItemMeta(meta);
        }
        return item;
    }
}
