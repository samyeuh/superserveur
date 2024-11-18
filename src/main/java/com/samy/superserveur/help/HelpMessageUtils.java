package com.samy.superserveur.help;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpMessageUtils {

    private static String header = ChatColor.LIGHT_PURPLE + "======= Aide =======";
    private static String footer = ChatColor.LIGHT_PURPLE + "====================";

    public static void helpStart(Player player){
        player.sendMessage(header);
    }

    public static void helpCommand(Player player, String command, String description, String usage){
        player.sendMessage( ChatColor.AQUA + " ● /" + command + ChatColor.RESET + " - " + ChatColor.WHITE + description + ChatColor.GRAY + " (" + usage + ") ");
    }

    public static void helpEnd(Player player){
        player.sendMessage(footer);
    }
}
