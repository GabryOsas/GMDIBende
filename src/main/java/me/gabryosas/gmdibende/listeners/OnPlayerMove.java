package me.gabryosas.gmdibende.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class OnPlayerMove implements Listener {
    public static HashMap<Player, Boolean> checkMove = new HashMap<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        Player player = e.getPlayer();
        if(e.getFrom().toVector().equals(e.getTo().toVector())) {
            checkMove.put(player, false);
            return;
        }
        checkMove.put(player, true);
    }
}