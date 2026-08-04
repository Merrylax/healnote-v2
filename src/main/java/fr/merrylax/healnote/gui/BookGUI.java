package fr.merrylax.healnote.gui;

import fr.merrylax.healnote.HealNoteV2;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BookGUI {

    private final HealNoteV2 plugin;
    private final Map<UUID, BookData> activeGuis = new HashMap<>();

    public BookGUI(HealNoteV2 plugin) {
        this.plugin = plugin;
    }

    public void openBookGUI(Player player, String bookType, String bookId) {
        BookData data = new BookData(bookType, bookId, player.getName());
        activeGuis.put(player.getUniqueId(), data);

        player.sendMessage("§6§l═══════════════════════════════════════");
        player.sendMessage("§6Écrivez le pseudo du joueur sur la ligne 1");
        player.sendMessage("§6Écrivez le temps (3-3600 sec) sur la ligne 2");
        player.sendMessage("§6Appuyez sur §aEntrée §6pour valider");
        player.sendMessage("§6Appuyez sur §cÉchap §6pour annuler");
        player.sendMessage("§6§l═══════════════════════════════════════");
    }

    public BookData getActiveGui(UUID playerUuid) {
        return activeGuis.get(playerUuid);
    }

    public void removeActiveGui(UUID playerUuid) {
        activeGuis.remove(playerUuid);
    }

    public static class BookData {
        public String bookType;
        public String bookId;
        public String owner;
        public String targetPlayer = "";
        public String timeInput = "";
        public int currentLine = 1;

        public BookData(String bookType, String bookId, String owner) {
            this.bookType = bookType;
            this.bookId = bookId;
            this.owner = owner;
        }
    }
}
