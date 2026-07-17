package fr.merrylax.healnote.managers;

import fr.merrylax.healnote.HealNoteV2;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DeathManager {

    private final HealNoteV2 plugin;
    private final Map<UUID, Integer> deathCountdown = new HashMap<>();
    private final Map<UUID, World> curseWorlds = new HashMap<>();
    private final Map<UUID, String> curseBy = new HashMap<>();

    public DeathManager(HealNoteV2 plugin) {
        this.plugin = plugin;
    }

    public void startTask() {
        Bukkit.getScheduler().runTaskTimer(plugin, this::updateCountdown, 0L, 20L);
    }

    private void updateCountdown() {
        deathCountdown.entrySet().removeIf(entry -> {
            UUID uuid = entry.getKey();
            int time = entry.getValue();
            Player player = Bukkit.getPlayer(uuid);

            if (player == null || !player.isOnline()) {
                cleanup(uuid);
                return true;
            }

            if (time <= 0) {
                executeSentence(player);
                cleanup(uuid);
                return true;
            }

            // Effets visuels
            if (time % 20 == 0) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 1, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 20, 1, false, false));
            }

            // Message barre d'action
            player.sendActionBar("§c⏳ Condamné à mort: §f" + time + "s");

            // Dégâts du temps (7.5 cœurs = 15 dégâts)
            if (time <= 30 && time % 10 == 0) {
                player.damage(2.0);
            }

            return false;
        });

        deathCountdown.replaceAll((uuid, time) -> time - 1);
    }

    public void cursePlayer(Player player, int seconds, String curseBy) {
        if (deathCountdown.containsKey(player.getUniqueId())) return;

        deathCountdown.put(player.getUniqueId(), seconds);
        curseWorlds.put(player.getUniqueId(), player.getWorld());
        DeathManager.this.curseBy.put(player.getUniqueId(), curseBy);

        // Effets mondiaux
        World world = player.getWorld();
        world.setStorm(true);
        world.setThundering(true);
        world.setTime(13000);

        Bukkit.broadcastMessage("§c§l════════════════════════════════════════");
        Bukkit.broadcastMessage("§c§l✦ UN DEATH NOTE A ÉTÉ UTILISÉ ! ✦");
        Bukkit.broadcastMessage("§c§l════════════════════════════════════════");
        Bukkit.broadcastMessage("§cLa nuit tombe. L'orage éclate.");
        Bukkit.broadcastMessage("§c§l" + player.getName() + " a " + seconds + " secondes à vivre...");
        Bukkit.broadcastMessage("§c§l════════════════════════════════════════");
    }

    private void executeSentence(Player player) {
        String executedBy = curseBy.getOrDefault(player.getUniqueId(), "Inconnu");
        
        player.setHealth(0);
        player.banPlayer("§c§lCondamné par le Death Note");

        Bukkit.broadcastMessage("§c§l════════════════════════════════════════");
        Bukkit.broadcastMessage("§c§l✦ LE SORT A ÉTÉ EXÉCUTÉ ! ✦");
        Bukkit.broadcastMessage("§c§l" + player.getName() + " est mort et banni pour l'éternité.");
        Bukkit.broadcastMessage("§c§l════════════════════════════════════════");
    }

    private void cleanup(UUID uuid) {
        deathCountdown.remove(uuid);
        curseWorlds.remove(uuid);
        curseBy.remove(uuid);
    }

    public boolean isCursed(UUID uuid) {
        return deathCountdown.containsKey(uuid);
    }

    public int getTimeRemaining(UUID uuid) {
        return deathCountdown.getOrDefault(uuid, -1);
    }
}