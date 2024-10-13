package me.piglinstudio.judgmentnight.util;

public class ChatColor {
    public static String getMsg(String str){
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', str);
    }
}
