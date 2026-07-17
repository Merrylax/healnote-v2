package fr.merrylax.healnote.listeners;

import fr.merrylax.healnote.HealNoteV2;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class HealNoteListener implements Listener {

    private final HealNoteV2 plugin;

    public HealNoteListener(HealNoteV2 plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onHealNoteUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (!event.hasItem()) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getItemMeta() == null) return;
        if (!item.getItemMeta().getDisplayName().contains("💚 Heal Note")) return;

        if (!player.hasPermission("healnote.use")) {
            player.sendMessage("§cVous n'avez pas la permission.");
            return;
        }

        // Chercher la cible
        Entity targetEntity = player.getTargetEntity(50, true);
        if (!(targetEntity instanceof Player)) {
            player.sendMessage("§cAucune cible joueur trouvée.");
            return;
        }

        Player target = (Player) targetEntity;

        if (!plugin.getDeathManager().isCursed(target.getUniqueId())) {
            player.sendMessage("§cCe joueur n'est pas maudit.");
            return;
        }

        // Sauver la cible au prix du sauveur
        Bukkit.broadcastMessage("§a§l════════════════════════════════════════");
        Bukkit.broadcastMessage("§a§l✦ LE HEAL NOTE A SAUVÉ " + target.getName() + " ! ✦");
        Bukkit.broadcastMessage("§a§lMais " + player.getName() + " meurt à sa place et est banni...");
        Bukkit.broadcastMessage("§a§l════════════════════════════════════════");

        // JUSTE LE SAUVEUR meurt et est banni
        player.setHealth(0);
        plugin.getBanManager().banPlayer(player.getName(), "Sacrifié pour sauver un joueur");

        // LA CIBLE EST SAUVÉE (pas bannie)

        item.setAmount(item.getAmount() - 1);
        event.setCancelled(true);
    }
}