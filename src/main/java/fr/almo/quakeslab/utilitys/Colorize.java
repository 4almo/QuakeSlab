package fr.almo.quakeslab.utilitys;

import org.bukkit.ChatColor;

public class Colorize {

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public String stripColor(String string) {
        return ChatColor.stripColor(string);
    }
}
