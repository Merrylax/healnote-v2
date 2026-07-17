package fr.merrylax.healnote.managers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BanManager {

    private final JavaPlugin plugin;

    public BanManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void banPlayer(String playerName, String reason) {
        Bukkit.getBanList(org.bukkit.BanList.Type.NAME).addBan(playerName, reason, null, null);
    }

    public void unbanPlayer(String playerName) {
        if (Bukkit.getBanList(org.bukkit.BanList.Type.NAME).isBanned(playerName)) {
            Bukkit.getBanList(org.bukkit.BanList.Type.NAME).pardon(playerName);
        }
    }

    public boolean isBanned(String playerName) {
        return Bukkit.getBanList(org.bukkit.BanList.Type.NAME).isBanned(playerName);
    }
}