package fr.merrylax.healnote.listeners;

import fr.merrylax.healnote.HealNoteV2;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Warden;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class GuardianListener implements Listener {

    private final HealNoteV2 plugin;

    public GuardianListener(HealNoteV2 plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onGuardianDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();

        if (!(entity instanceof Warden)) return;
        if (entity.getCustomName() == null) return;
        if (!entity.getCustomName().contains("Gardien du Destin")) return;

        // Le Gardien est vaincu!
        Bukkit.broadcastMessage("§a§l════════════════════════════════════════");
        Bukkit.broadcastMessage("§a§l✦ LE GARDIEN DU DESTIN A ÉTÉ VAINCU ! ✦");
        Bukkit.broadcastMessage("§a§lLe joueur est débanni !");
        Bukkit.broadcastMessage("§a§l════════════════════════════════════════");
    }
}