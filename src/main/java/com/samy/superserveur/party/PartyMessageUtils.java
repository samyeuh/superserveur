package com.samy.superserveur.party;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PartyMessageUtils {
    private static final ChatColor headerColor = ChatColor.AQUA;
    private static final ChatColor secondaryColor = ChatColor.LIGHT_PURPLE;
    private static final String partyHeader = headerColor + "[Groupe] " + ChatColor.RESET;

    public static String formatName(String name){
        return headerColor + name + ChatColor.RESET;
    }

    public static TextComponent formatCommand(String command, String text, String hover){
        return getTextComponent(command, text, hover, partyHeader, secondaryColor);
    }

    public static TextComponent getTextComponent(String command, String text, String hover, String partyHeader, ChatColor secondaryColor) {
        TextComponent result = new TextComponent(partyHeader + "[" + secondaryColor);
        result.addExtra(text);
        result.addExtra(ChatColor.WHITE + "]");
        result.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        result.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(hover)));
        return result;
    }

    public static void playerNoParty(Player player){
        player.sendMessage(partyHeader + ChatColor.RED + "Vous n'êtes pas dans un groupe.");
    }

    public static void partyRequest(Player player, Player target){
        player.sendMessage(partyHeader + "Demande envoyée à " + formatName(target.getName()));
        target.sendMessage(partyHeader + formatName(player.getName()) + " vous invite à rejoindre son groupe.");
        target.spigot().sendMessage(formatCommand("/party accept " +  player.getName(), ChatColor.GREEN + "Cliquez ici pour accepter", "Accepter la demande de " + player.getName()));
    }

    public static void partyList(Player player, List<String> members){
        player.sendMessage(partyHeader + "Votre groupe:");
        player.sendMessage(secondaryColor + "===============");

        String leaderTag = ChatColor.GRAY + " (Chef)" + ChatColor.RESET;
        String leaderName = members.get(0);
        List<String> partyMembers = new ArrayList<>(List.of(leaderName));

        for (String m : members.subList(1, members.size())) {
            if (Bukkit.getPlayer(m) != null) {
                partyMembers.add(1, m);
            } else {
                partyMembers.add(m);
            }
        }

        for (String m : partyMembers) {
            boolean leader = m.equals(leaderName);
            String member = ChatColor.WHITE + m + ChatColor.RESET;
            String connectPoint = Bukkit.getPlayer(m) != null ?
                   ChatColor.GREEN + " ● " + ChatColor.WHITE : ChatColor.RED + " ● " + ChatColor.GRAY;
            if (leader) member+= leaderTag;
            player.sendMessage(connectPoint + member);
        }
        player.sendMessage(secondaryColor + "===============");
    }

    public static void playerJoinParty(Player player, Party party){
        UUID leaderId = party.getLeaderId();
        Player leader = player.getServer().getPlayer(leaderId);
        assert leader != null;
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
