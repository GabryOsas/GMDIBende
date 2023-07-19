package me.gabryosas.gmdibende.listeners;

import me.gabryosas.gmdibende.GMDIBende;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        FileConfiguration config = GMDIBende.plugin.getConfig();
        if (!GMDIBende.plugin.getConfig().getBoolean("Settings.Credits")) return;
        if (player.hasPermission(config.getString("Permission.Give-permission"))) return;
        player.sendMessage("§9§lGMDIDev §7Plugin §a§lBENDE §7Sviluppato da §e@GabryOsas / @GMDIdevelopment");
    }
}
