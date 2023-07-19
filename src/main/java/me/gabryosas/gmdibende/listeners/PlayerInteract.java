package me.gabryosas.gmdibende.listeners;

import me.gabryosas.gmdibende.GMDIBende;
import me.gabryosas.gmdibende.utils.Color;
import me.gabryosas.gmdibende.utils.Config;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerInteract implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getHand() != EquipmentSlot.HAND) return;
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_AIR) return;
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (itemInHand == null || itemInHand.getType() == Material.AIR) return;
        if (!isBandage(itemInHand)) {
            return;
        }
        if (!getBandagePermissionByItemName(itemInHand.getItemMeta().getDisplayName()).equalsIgnoreCase("none") && !player.hasPermission(getBandagePermissionByItemName(itemInHand.getItemMeta().getDisplayName()))){
            player.sendMessage(Config.NO_PERMISSION_MESSAGE);
            return;
        }
        if (itemInHand.getAmount() > 1) {
            itemInHand.setAmount(itemInHand.getAmount() - 1);
        } else {
            player.getInventory().remove(itemInHand);
        }
        startCooldown(player, itemInHand);
    }
    private boolean isBandage(ItemStack item) {
        FileConfiguration config = GMDIBende.plugin.getConfig();
        ConfigurationSection bandagesSection = config.getConfigurationSection("Bandages");
        if (bandagesSection != null) {
            for (String bandageName : bandagesSection.getKeys(false)) {
                String itemName = Color.translateHexColorCodes(GMDIBende.plugin.getConfig().getString("Bandages." + bandageName + ".Item-name"));
                if (item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(itemName)) {
                    return true;
                }
            }
        }
        return false;
    }
    private String getBandagePermissionByItemName(String itemName) {
        FileConfiguration config = GMDIBende.plugin.getConfig();
        ConfigurationSection bandagesSection = config.getConfigurationSection("Bandages");

        if (bandagesSection != null) {
            for (String bandageName : bandagesSection.getKeys(false)) {
                String bandageItemName = Color.translateHexColorCodes(GMDIBende.plugin.getConfig().getString("Bandages." + bandageName + ".Item-name"));
                if (itemName.equals(bandageItemName)) {
                    return config.getString("Bandages." + bandageName + ".Permission-use");
                }
            }
        }

        return "none";
    }
    private int getBandageTime(String itemName) {
        FileConfiguration config = GMDIBende.plugin.getConfig();
        ConfigurationSection bandagesSection = config.getConfigurationSection("Bandages");
        if (bandagesSection != null) {
            for (String bandageName : bandagesSection.getKeys(false)) {
                String bandageItemName = Color.translateHexColorCodes(GMDIBende.plugin.getConfig().getString("Bandages." + bandageName + ".Item-name"));
                if (itemName.equals(bandageItemName)) {
                    return config.getInt("Bandages." + bandageName + ".Time");
                }
            }
        }
        return 0;
    }
    private int getBandageHealth(String itemName) {
        FileConfiguration config = GMDIBende.plugin.getConfig();
        ConfigurationSection bandagesSection = config.getConfigurationSection("Bandages");
        if (bandagesSection != null) {
            for (String bandageName : bandagesSection.getKeys(false)) {
                String bandageItemName = Color.translateHexColorCodes(GMDIBende.plugin.getConfig().getString("Bandages." + bandageName + ".Item-name"));
                if (itemName.equals(bandageItemName)) {
                    return config.getInt("Bandages." + bandageName + ".Health");
                }
            }
        }
        return 0;
    }
    private int getBandageHunger(String itemName) {
        FileConfiguration config = GMDIBende.plugin.getConfig();
        ConfigurationSection bandagesSection = config.getConfigurationSection("Bandages");
        if (bandagesSection != null) {
            for (String bandageName : bandagesSection.getKeys(false)) {
                String bandageItemName = Color.translateHexColorCodes(GMDIBende.plugin.getConfig().getString("Bandages." + bandageName + ".Item-name"));
                if (itemName.equals(bandageItemName)) {
                    return config.getInt("Bandages." + bandageName + ".Hunger");
                }
            }
        }
        return 0;
    }
    private void startCooldown(Player player, ItemStack itemInHand){
        String type = GMDIBende.plugin.getConfig().getString("Settings.Cooldown-message-type").toUpperCase();
        OnPlayerMove.checkMove.put(player, false);
        new BukkitRunnable() {
            int remainingTime = getBandageTime(itemInHand.getItemMeta().getDisplayName());
            @Override
            public void run() {
                if (remainingTime == 0) {
                    cancel();
                    if (!GMDIBende.plugin.getConfig().getBoolean("Settings.Disable-move")){
                        setStats(player, getBandageHunger(itemInHand.getItemMeta().getDisplayName()), getBandageHealth(itemInHand.getItemMeta().getDisplayName()));
                        player.sendMessage(Config.USE_MESSAGE);
                        return;
                    }
                    if (!OnPlayerMove.checkMove.get(player)){
                        setStats(player, getBandageHunger(itemInHand.getItemMeta().getDisplayName()), getBandageHealth(itemInHand.getItemMeta().getDisplayName()));
                        player.sendMessage(Config.USE_MESSAGE);
                        return;
                    }
                    if (type.equalsIgnoreCase("TITLE")){
                        String TITLE_FAIL_MESSAGE = Color.translateHexColorCodes(GMDIBende.plugin.getConfig().getString("Messages.Title-failed-message"));
                        String SUBTITLE_FAIL_MESSAGE = Color.translateHexColorCodes(GMDIBende.plugin.getConfig().getString("Messages.Subtitle-failed-message"));
                        player.sendTitle(TITLE_FAIL_MESSAGE, SUBTITLE_FAIL_MESSAGE, 0 , 20, 0);
                        return;
                    }
                    String ACTIONBAR_FAIL_MESSAGE = Color.translateHexColorCodes(GMDIBende.plugin.getConfig().getString("Messages.Action-bar-failed-message!"));
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ACTIONBAR_FAIL_MESSAGE));
                    return;
                }
                if (type.equalsIgnoreCase("TITLE")){
                    String TITLE_MESSAGE = Color.translateHexColorCodes(GMDIBende.plugin.getConfig().getString("Messages.Title-message").replace("%time%", String.valueOf(remainingTime)));
                    String SUBTITLE_MESSAGE = Color.translateHexColorCodes(GMDIBende.plugin.getConfig().getString("Messages.Subtitle-message").replace("%time%", String.valueOf(remainingTime)));
                    player.sendTitle(TITLE_MESSAGE, SUBTITLE_MESSAGE, 0, 20,0);
                }else if (type.equalsIgnoreCase("ACTION-BAR")){
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Color.translateHexColorCodes(GMDIBende.plugin.getConfig().getString("Messages.Action-bar-message").replace("%time%", String.valueOf(remainingTime)))));
                }
                remainingTime--;
            }
        }.runTaskTimer(GMDIBende.plugin, 0L, 20L);
    }
    public static void setStats(Player player, int hunger, double health){
        int newFood = player.getFoodLevel() + hunger;
        if (newFood > 20) {
            player.setFoodLevel(20);
        } else {
            player.setFoodLevel(newFood);
        }
        double newHealth = player.getHealth() + health;
        if (newHealth > 20.0) {
            player.setHealth(20.0);
        } else {
            player.setHealth(newHealth);
        }
    }
}
