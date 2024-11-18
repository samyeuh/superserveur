package com.samy.superserveur.party;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class PartyMessageUtils {
    private static final ChatColor headerColor = ChatColor.AQUA;
    private static final ChatColor secondaryColor = ChatColor.LIGHT_PURPLE;
    private static final String partyHeader = headerColor + "[Groupe] " + ChatColor.RESET;

    public static String formatName(String name){
        return headerColor + name + ChatColor.RESET;
    }

    public static String formatCommand(String command){
        return secondaryColor + command + ChatColor.RESET;
    }

    public static void playerNoParty(Player player){
        player.sendMessage(partyHeader + ChatColor.RED + "Vous n'êtes pas dans un groupe.");
    }

    public static void partyRequest(Player player, Player target){
        player.sendMessage(partyHeader + "Demande envoyée à " + formatName(target.getName()));
        target.sendMessage(partyHeader + formatName(player.getName()) + " vous invite à rejoindre son groupe.");
        target.sendMessage("Tapez" + formatCommand(" /party accept " +  player.getName()) + " pour rejoindre le groupe.");
    }

    public static void partyList(Player player, List<String> members){
        // mettre les joueurs connectés en haut de la liste sauf le chef
        player.sendMessage(partyHeader + "Votre groupe:");
        player.sendMessage(secondaryColor + "===============");
        int index = 0;
        String leader = ChatColor.GRAY + " (Chef)" + ChatColor.RESET;
        for (String m : members) {
            String member = ChatColor.WHITE + m + ChatColor.RESET;
            String connectPoint = Bukkit.getPlayer(m) != null ?
                    ChatColor.GREEN + " ● " + ChatColor.WHITE : ChatColor.RED + " ● " + ChatColor.GRAY;
            player.sendMessage(connectPoint + member + (index==0 ? leader : ""));
            index++;
        }
        player.sendMessage(secondaryColor + "===============");
    }

    public static void playerJoinParty(Player player, Party party){
        UUID leaderId = party.getLeaderId();
        Player leader = player.getServer().getPlayer(leaderId);
        player.sendMessage(partyHeader + "Vous avez rejoint le groupe de " + formatName(leader.getName()) + " !");
        leader.sendMessage(partyHeader + formatName(player.getName()) + " a rejoint votre groupe !");
    }

    public static void partyError(Player player, String error){
        player.sendMessage(partyHeader + ChatColor.RED + error);
    }

    public static void partyInfo(Player player, String message){
        player.sendMessage(partyHeader + ChatColor.WHITE + message);
    }

    public static void memberConnected(Player player, Player memberConnected){
        String memberName = ChatColor.GREEN + memberConnected.getName() + ChatColor.RESET;
        player.sendMessage(partyHeader + memberName + " s'est connecté !");
    }

    public static void memberDisconnected(Player player, Player memberDisconnected){
        String memberName = ChatColor.RED + memberDisconnected.getName() + ChatColor.RESET;
        player.sendMessage(partyHeader + memberName + " s'est déconnecté !");
    }


}
