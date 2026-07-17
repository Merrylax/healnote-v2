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

public class DeathNoteListener implements Listener {

    private final HealNoteV2 plugin;

    public DeathNoteListener(HealNoteV2 plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeathNoteUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (!event.hasItem()) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getItemMeta() == null) return;
        if (!item.getItemMeta().getDisplayName().contains("☠️ Death Note")) return;

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

        if (target.hasPermission("healnote.immune")) {
            player.sendMessage("§cCe joueur est immunisé.");
            return;
        }

        if (plugin.getDeathManager().isCursed(target.getUniqueId())) {
            player.sendMessage("§cCe joueur est déjà maudit.");
            return;
        }

        // Appliquer la malédiction
        plugin.getDeathManager().cursePlayer(target, 120, player.getName());
        item.setAmount(item.getAmount() - 1);
        
        event.setCancelled(true);
    }
}