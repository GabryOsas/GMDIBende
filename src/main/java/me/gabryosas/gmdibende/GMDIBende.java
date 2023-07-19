package me.gabryosas.gmdibende;

import me.gabryosas.gmdibende.commands.Bende;
import me.gabryosas.gmdibende.listeners.OnJoin;
import me.gabryosas.gmdibende.listeners.OnPlayerMove;
import me.gabryosas.gmdibende.listeners.PlayerInteract;
import me.gabryosas.gmdibende.utils.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public final class GMDIBende extends JavaPlugin {
    public static GMDIBende plugin;

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        this.getCommand("benda").setExecutor(new Bende());
        this.getCommand("benda").setTabCompleter(new TabCompleter());
        getServer().getPluginManager().registerEvents(new OnJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerMove(), this);
    }

    @Override
    public void onDisable() {
    }
}
