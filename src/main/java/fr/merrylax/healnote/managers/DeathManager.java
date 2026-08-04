package fr.merrylax.healnote.managers;

import fr.merrylax.healnote.HealNoteV2;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
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
    private final Map<UUID, Player> curserPlayer = new HashMap<>();

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

            // Particules et éclairs
            spawnDeathEffects(player);
            
            // Éclairs toutes les 2 secondes
            if (time % 2 == 0) {
                spawnLightning(player.getLocation());
                Player curser = curserPlayer.get(uuid);
                if (curser != null && curser.isOnline()) {
                    spawnLightning(curser.getLocation());
                }
            }

            return false;
        });

        deathCountdown.replaceAll((uuid, time) -> time - 1);
    }

    private void spawnDeathEffects(Player player) {
        Location loc = player.getLocation().add(0, 1, 0);
        World world = player.getWorld();
        
        // Particules rouges autour du joueur
        world.spawnParticle(Particle.REDSTONE, loc, 20, 1, 1, 1, 0, new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1.5f));
        
        // Particules de portail (effet mystique)
        world.spawnParticle(Particle.REVERSE_PORTAL, loc, 15, 0.5, 1, 0.5, 0.1);
        
        // Particules de fumée
        world.spawnParticle(Particle.SMOKE_LARGE, loc, 5, 0.5, 1, 0.5, 0.05);
        
        // Son sinistre
        player.playSound(loc, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.8f);
    }

    private void spawnLightning(Location location) {
        World world = location.getWorld();
        world.strikeLightning(location);
        
        // Son d'éclair
        world.playSound(location, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f);
        world.playSound(location, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1.0f, 1.0f);
    }

    public void cursePlayer(Player player, int seconds, String curseByName) {
        if (deathCountdown.containsKey(player.getUniqueId())) return;

        deathCountdown.put(player.getUniqueId(), seconds);
        curseWorlds.put(player.getUniqueId(), player.getWorld());
        DeathManager.this.curseBy.put(player.getUniqueId(), curseByName);
        
        // Stocker le joueur qui a lancé le sort
        Player curser = Bukkit.getPlayer(curseByName);
        if (curser != null) {
            curserPlayer.put(player.getUniqueId(), curser);
        }

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
        
        // Effets de mort spectaculaires
        Location deathLoc = player.getLocation().add(0, 1, 0);
        World world = player.getWorld();
        
        // Nombreux éclairs
        for (int i = 0; i < 5; i++) {
            world.strikeLightning(deathLoc.add(Math.random() * 5 - 2.5, 0, Math.random() * 5 - 2.5));
        }
        
        // Particules explosives
        world.spawnParticle(Particle.REDSTONE, deathLoc, 100, 2, 2, 2, 0, new Particle.DustOptions(Color.fromRGB(255, 0, 0), 2.0f));
        world.spawnParticle(Particle.EXPLOSION_LARGE, deathLoc, 10, 1, 1, 1, 0.1);
        world.spawnParticle(Particle.SMOKE_LARGE, deathLoc, 50, 2, 2, 2, 0.1);
        
        // Son de mort
        world.playSound(deathLoc, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 2.0f, 0.5f);
        world.playSound(deathLoc, Sound.ENTITY_WITHER_SPAWN, 2.0f, 0.8f);
        
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
        curserPlayer.remove(uuid);
    }

    public boolean isCursed(UUID uuid) {
        return deathCountdown.containsKey(uuid);
    }

    public int getTimeRemaining(UUID uuid) {
        return deathCountdown.getOrDefault(uuid, -1);
    }
}
