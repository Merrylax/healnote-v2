package fr.merrylax.healnote.listeners;

import fr.merrylax.healnote.HealNoteV2;
import fr.merrylax.healnote.gui.BookGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class BookListener implements Listener {

    private final HealNoteV2 plugin;
    private final BookGUI bookGUI;

    public BookListener(HealNoteV2 plugin) {
        this.plugin = plugin;
        this.bookGUI = new BookGUI(plugin);
    }

    @EventHandler
    public void onBookUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (!event.hasItem()) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getItemMeta() == null) return;

        String bookType = plugin.getItemManager().getBookType(item);
        if (bookType == null) return;

        String bookId = plugin.getItemManager().getBookId(item);
        if (bookId == null) return;

        if (!player.hasPermission("healnote.use")) {
            player.sendMessage("§cVous n'avez pas la permission.");
            return;
        }

        // Ouvrir la GUI du livre
        bookGUI.openBookGUI(player, bookType, bookId);
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        BookGUI.BookData guiData = bookGUI.getActiveGui(player.getUniqueId());

        if (guiData == null) return;

        event.setCancelled(true);
        String message = event.getMessage();

        // Ligne 1 : Pseudo du joueur
        if (guiData.currentLine == 1) {
            if (message.equalsIgnoreCase("annuler")) {
                bookGUI.removeActiveGui(player.getUniqueId());
                player.sendMessage("§cOpération annulée.");
                return;
            }
            guiData.targetPlayer = message;
            guiData.currentLine = 2;
            player.sendMessage("§a✓ Pseudo enregistré: §f" + message);
            player.sendMessage("§6Maintenant, écrivez le temps (3-3600 secondes)");
            return;
        }

        // Ligne 2 : Temps
        if (guiData.currentLine == 2) {
            if (message.equalsIgnoreCase("annuler")) {
                bookGUI.removeActiveGui(player.getUniqueId());
                player.sendMessage("§cOpération annulée.");
                return;
            }

            try {
                int time = Integer.parseInt(message);
                if (time < 3 || time > 3600) {
                    player.sendMessage("§cLe temps doit être entre 3 et 3600 secondes!");
                    return;
                }
                guiData.timeInput = message;
                guiData.currentLine = 3;
                player.sendMessage("§a✓ Temps enregistré: §f" + time + "s");
                executeBook(player, guiData);
                bookGUI.removeActiveGui(player.getUniqueId());
            } catch (NumberFormatException e) {
                player.sendMessage("§cVeuillez entrer un nombre valide!");
            }
        }
    }

    private void executeBook(Player player, BookGUI.BookData guiData) {
        String bookType = guiData.bookType;
        String targetName = guiData.targetPlayer;
        int time = Integer.parseInt(guiData.timeInput);

        Player target = Bukkit.getPlayer(targetName);

        if (bookType.equals("death_note")) {
            if (target == null) {
                player.sendMessage("§c❌ Joueur non trouvé ou hors ligne.");
                return;
            }

            if (target.hasPermission("healnote.immune")) {
                player.sendMessage("§cCe joueur est immunisé.");
                return;
            }

            if (plugin.getDeathManager().isCursed(target.getUniqueId())) {
                player.sendMessage("§cCe joueur est déjà maudit.");
                return;
            }

            plugin.getDeathManager().cursePlayer(target, time, player.getName());
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
            player.sendMessage("§a✓ Death Note utilisé sur §f" + targetName + " §apour §f" + time + "s");
        }

        else if (bookType.equals("heal_note")) {
            if (target == null) {
                player.sendMessage("§c❌ Joueur non trouvé ou hors ligne.");
                return;
            }

            if (!plugin.getDeathManager().isCursed(target.getUniqueId())) {
                player.sendMessage("§cCe joueur n'est pas maudit.");
                return;
            }

            Bukkit.broadcastMessage("§a§l═══════════════════════════════════════");
            Bukkit.broadcastMessage("§a§l✦ LE HEAL NOTE A SAUVÉ " + target.getName() + " ! ✦");
            Bukkit.broadcastMessage("§a§lMais " + player.getName() + " meurt à sa place et est banni...");
            Bukkit.broadcastMessage("§a§l═══════════════════════════════════════");

            player.setHealth(0);
            plugin.getBanManager().banPlayer(player.getName(), "Sacrifié pour sauver un joueur");
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }

        else if (bookType.equals("deban_book")) {
            Bukkit.broadcastMessage("§6§l═══════════════════════════════════════");
            Bukkit.broadcastMessage("§6§l👹 LE GARDIEN DU DESTIN EST INVOQUÉ ! 👹");
            Bukkit.broadcastMessage("§6§lDéfi de " + time + " secondes pour débannir " + targetName);
            Bukkit.broadcastMessage("§6§lVaincre le Gardien = Salut du joueur");
            Bukkit.broadcastMessage("§6§l═══════════════════════════════════════");

            plugin.getGuardianManager().spawnGuardian(player.getLocation(), targetName, java.util.UUID.randomUUID(), time);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
            player.sendMessage("§a✓ Deban Book utilisé pour " + time + " secondes");
        }
    }

    public BookGUI getBookGUI() {
        return bookGUI;
    }
}
