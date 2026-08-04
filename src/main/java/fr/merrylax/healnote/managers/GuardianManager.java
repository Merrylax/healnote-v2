package fr.merrylax.healnote.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Warden;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.attribute.Attribute;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GuardianManager {

    private final JavaPlugin plugin;
    private final Map<UUID, Integer> activeChallenges = new HashMap<>();
    private final Map<UUID, Warden> guardians = new HashMap<>();

    public GuardianManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void startTask() {
        Bukkit.getScheduler().runTaskTimer(plugin, this::updateChallenges, 0L, 20L);
    }

    public void spawnGuardian(Location location, String targetName, UUID challengeId, int duration) {
        Warden guardian = (Warden) location.getWorld().spawnEntity(location, EntityType.WARDEN);
        
        // Configuration du Gardien
        guardian.setCustomName("§6👹 Gardien du Destin");
        guardian.setCustomNameVisible(true);
        
        // Stats personnalisées
        guardian.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(384);
        guardian.setHealth(384);
        guardian.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(32);
        guardian.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.4);
        
        guardians.put(challengeId, guardian);
        activeChallenges.put(challengeId, duration);

        Bukkit.broadcastMessage("§6§l═══════════════════════════════════════");
        Bukkit.broadcastMessage("§6§l👹 LE GARDIEN DU DESTIN EST INVOQUÉ ! 👹");
        Bukkit.broadcastMessage("§6§lDéfi de " + duration + " SECONDES pour débannir " + targetName);
        Bukkit.broadcastMessage("§6§lVaincre le Gardien = Salut du joueur");
        Bukkit.broadcastMessage("§6§l═══════════════════════════════════════");
    }

    private void updateChallenges() {
        activeChallenges.entrySet().removeIf(entry -> {
            UUID id = entry.getKey();
            int time = entry.getValue();

            if (time <= 0) {
                failChallenge(id);
                return true;
            }

            return false;
        });

        activeChallenges.replaceAll((id, time) -> time - 1);
    }

    public void completeChallenge(UUID id, String targetName) {
        activeChallenges.remove(id);
        Warden guardian = guardians.remove(id);
        if (guardian != null) {
            guardian.remove();
        }

        Bukkit.broadcastMessage("§a§l═══════════════════════════════════════");
        Bukkit.broadcastMessage("§a§l✦ LE GARDIEN A ÉTÉ VAINCU ! ✦");
        Bukkit.broadcastMessage("§a§l" + targetName + " EST DÉBANNI !");
        Bukkit.broadcastMessage("§a§l═══════════════════════════════════════");
    }

    private void failChallenge(UUID id) {
        Warden guardian = guardians.remove(id);
        if (guardian != null) {
            guardian.remove();
        }

        Bukkit.broadcastMessage("§c§l═══════════════════════════════════════");
        Bukkit.broadcastMessage("§c§l❌ LE GARDIEN N'A PAS ÉTÉ VAINCU !");
        Bukkit.broadcastMessage("§c§lLe joueur reste banni à jamais.");
        Bukkit.broadcastMessage("§c§l═══════════════════════════════════════");
    }

    public boolean hasActiveChallenge(UUID id) {
        return activeChallenges.containsKey(id);
    }
}
