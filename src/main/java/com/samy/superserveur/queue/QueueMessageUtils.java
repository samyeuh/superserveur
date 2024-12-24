package com.samy.superserveur.queue;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class QueueMessageUtils {

    private static final String header = ChatColor.LIGHT_PURPLE + "[File d'attente] ";
    private static final String joiningQueue = ChatColor.AQUA + "Vous avez rejoint la file d'attente pour le jeu " + ChatColor.GOLD;
    private static final String leavingQueue = ChatColor.DARK_BLUE + "Vous avez quitté la file d'attente du jeu " + ChatColor.GOLD;


    public static String getHeader() {
        return header;
    }

    public static void joiningQueue(Player player, String game){
        String msg = header + joiningQueue + game;
        player.sendMessage(msg);
    }

    public static void leavingQueue(Player player, String game){
        String msg = header + leavingQueue + game;
        player.sendMessage(msg);
    }
}
