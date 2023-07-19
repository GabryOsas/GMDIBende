package me.gabryosas.gmdibende.utils;

import me.gabryosas.gmdibende.GMDIBende;

public class Config {
    public static String PREFIX_CONFIG = Color.translateHexColorCodes(GMDIBende.plugin.getConfig().getString("Settings.Prefix"));
    public static String NO_PERMISSION_MESSAGE = Color.translateHexColorCodes(GMDIBende.plugin.getConfig().getString("Messages.No-permission-message").replace("%prefix%", PREFIX_CONFIG));
    public static String USE_MESSAGE = Color.translateHexColorCodes(GMDIBende.plugin.getConfig().getString("Messages.Use-message").replace("%prefix%", PREFIX_CONFIG));
    public static String RELOAD_MESSAGE = Color.translateHexColorCodes(GMDIBende.plugin.getConfig().getString("Messages.Reload-message").replace("%prefix%", PREFIX_CONFIG));
}
