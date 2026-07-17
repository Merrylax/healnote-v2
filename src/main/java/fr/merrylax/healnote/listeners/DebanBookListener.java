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
import java.util.UUID;

public class DebanBookListener implements Listener {

    private final HealNoteV2 plugin;

    public DebanBookListener(HealNoteV2 plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDebanBookUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (!event.hasItem()) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getItemMeta() == null) return;
        if (!item.getItemMeta().getDisplayName().contains("📖 Deban Book")) return;

        if (!player.hasPermission("healnote.deban")) {
            player.sendMessage("§cVous n'avez pas la permission.");
            return;
        }

        // Invoquer le Gardien
        UUID challengeId = UUID.randomUUID();
        plugin.getGuardianManager().spawnGuardian(player.getLocation(), player.getName(), challengeId);

        item.setAmount(item.getAmount() - 1);
        event.setCancelled(true);
    }
}