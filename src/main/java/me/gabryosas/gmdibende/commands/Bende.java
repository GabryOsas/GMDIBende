package me.gabryosas.gmdibende.commands;

import me.gabryosas.gmdibende.GMDIBende;
import me.gabryosas.gmdibende.utils.Color;
import me.gabryosas.gmdibende.utils.Config;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.io.FileUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Bende implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage("§cNon puoi eseguire questo comando dalla console!");
            return true;
        }
        Player player = (Player) sender;
        FileConfiguration config = GMDIBende.plugin.getConfig();
        if (args.length == 0){
            sendHelpMessage(player);
        }
        if (args.length >= 3){
            sendHelpMessage(player);
        }
        if (args.length == 1){
            if (!args[0].equalsIgnoreCase("reload")) {
                return true;
            }
            if (!player.hasPermission(config.getString("Permission.Give-permission"))){
                player.sendMessage(Config.NO_PERMISSION_MESSAGE);
                return true;
            }
            try {
                File originalConfig = new File(GMDIBende.plugin.getDataFolder(), "config.yml");
                File backupConfig = new File(GMDIBende.plugin.getDataFolder(), "config_backup.yml");
                FileUtils.copyFile(originalConfig, backupConfig);
                FileUtils.copyFile(backupConfig, originalConfig);
                GMDIBende.plugin.saveConfig();
                player.sendMessage(Config.RELOAD_MESSAGE);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        if (args.length == 2){
            if (!args[0].equalsIgnoreCase("give")) {
                return true;
            }
            if (!player.hasPermission(config.getString("Permission.Give-permission"))){
                player.sendMessage(Config.NO_PERMISSION_MESSAGE);
                return true;
            }
            if (!existBandage(args[1])){
                String NO_EXIST_MESSAGE = Color.translateHexColorCodes(GMDIBende.plugin.getConfig().getString("Messages.No-exist-bandage-message").replace("%prefix%", Config.PREFIX_CONFIG).replace("%name%", args[1]));
                player.sendMessage(NO_EXIST_MESSAGE);
                return true;
            }
            String GIVE_MESSAGE = Color.translateHexColorCodes(GMDIBende.plugin.getConfig().getString("Messages.Give-bandage-message").replace("%prefix%", Config.PREFIX_CONFIG).replace("%name%", args[1]));
            ItemStack bandage = getBandageItem(args[1]);
            player.getInventory().addItem(bandage);
            player.sendMessage(GIVE_MESSAGE);
        }
        return true;
    }
    private List<String> getFormattedLore(List<String> lore) {
        for (int i = 0; i < lore.size(); i++) {
            lore.set(i, Color.translateHexColorCodes(lore.get(i)));
        }
        return lore;
    }
    public ItemStack getBandageItem(String bandageName) {
        ItemStack bandage = new ItemStack(Material.valueOf(GMDIBende.plugin.getConfig().getString("Bandages." + bandageName + ".Item-type")));
        bandage.setAmount(GMDIBende.plugin.getConfig().getInt("Bandages." + bandageName + ".Item-amount"));
        ItemMeta bandageMeta = bandage.getItemMeta();
        bandageMeta.setDisplayName(Color.translateHexColorCodes(GMDIBende.plugin.getConfig().getString("Bandages." + bandageName + ".Item-name")));
        bandageMeta.setCustomModelData(GMDIBende.plugin.getConfig().getInt("Bandages." + bandageName + ".Item-model-data"));
        List<String> lore = GMDIBende.plugin.getConfig().getStringList("Bandages." + bandageName + ".Item-lore");
        bandageMeta.setLore(getFormattedLore(lore));
        bandage.setItemMeta(bandageMeta);
        return bandage;
    }
    public static void sendHelpMessage(Player player){
        FileConfiguration config = GMDIBende.plugin.getConfig();
        for (String message : config.getStringList("Messages.Help-message")) {
            String translatedMessage = Color.translateHexColorCodes(message.replace("%prefix%", Config.PREFIX_CONFIG));
            player.sendMessage(translatedMessage);
        }
        player.sendMessage("§7Plugin creato da §e@GabryOsas / @GMDIdevelopment");
    }
    public boolean existBandage(String bandageName) {
        FileConfiguration config = GMDIBende.plugin.getConfig();
        ConfigurationSection bandagesSection = config.getConfigurationSection("Bandages");
        if (bandagesSection != null) {
            if (bandagesSection.contains(bandageName)) {
                return true;
            }
        }
        return false;
    }
}
