package me.gabryosas.gmdibende.utils;

import me.gabryosas.gmdibende.GMDIBende;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1){
            completions.add("reload");
            completions.add("give");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
            FileConfiguration config = GMDIBende.plugin.getConfig();
            ConfigurationSection bandagesSection = config.getConfigurationSection("Bandages");
            if (bandagesSection != null) {
                for (String bandageName : bandagesSection.getKeys(false)) {
                    completions.add(bandageName);
                }
            }
        }
        return completions;
    }
}
