package com.samy.superserveur.party;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class PartyMessageUtils {

    private static final String partyHeader = ChatColor.GOLD + "[Groupe] " + ChatColor.RESET;

    public static void playerNoParty(Player player){
        player.sendMessage(partyHeader + ChatColor.RED + "Vous n'êtes pas dans un groupe.");
    }

    public static void partyList(Player player, List<String> members){
        player.sendMessage(partyHeader + "Votre groupe:");
        player.sendMessage(ChatColor.GOLD + "---------------------------------");
        for (String member : members) {
            player.sendMessage(ChatColor.GOLD + member + ChatColor.RESET);
        }
        player.sendMessage(ChatColor.GOLD + "---------------------------------");
    }

    public static void playerJoinParty(Player player, Party party){
        UUID leaderId = party.getLeaderId();
        Player leader = player.getServer().getPlayer(leaderId);
        player.sendMessage(partyHeader + "Vous avez rejoint le groupe de " + ChatColor.GOLD + leader.getName() + ChatColor.RESET + " !");
        leader.sendMessage(partyHeader + ChatColor.GOLD + player.getName() + ChatColor.RESET + " a rejoint votre groupe !");
    }

    public static void partyError(Player player, String error){
        player.sendMessage(partyHeader + ChatColor.RED + error);
    }

    public static void partyInfo(Player player, String message){
        player.sendMessage(partyHeader + ChatColor.WHITE + message);
    }


}
