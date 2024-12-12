package com.samy.superserveur.rank;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RankManager {

    private final HashMap<String, Rank> playersRank = new HashMap<>();
    private final List<Rank> rankList;

    public RankManager() {
        Rank joueur = new Rank("Joueur", Permissions.PLAYER, ChatColor.GRAY, "05_player");
        Rank vip = new Rank("VIP", Permissions.VIP, ChatColor.YELLOW, "04_vip");
        Rank vipplus = new Rank("VIP+", Permissions.VIPPLUS, ChatColor.AQUA, "03_vip+");
        Rank modo = new Rank("Modo", Permissions.MODO, ChatColor.RED, "02_mod");
        Rank admin = new Rank("Admin", Permissions.ADMIN, ChatColor.DARK_RED, "01_admin");
        this.rankList = Arrays.asList(admin, modo, vipplus, vip, joueur);
    }

    public List<Rank> getRanks(){
        return rankList;
    }

    public void setRank(Player player, Rank rank) {
        playersRank.put(player.getName(), rank);
        String name = rank.getColorName() + " " + player.getName();
        player.setDisplayName(name);
        player.setPlayerListName(name);
    }

    public Rank getRank(Player player) {
        return playersRank.getOrDefault(player.getName(), getJoueurRank());
    }

    public Rank findRank(String rankName){
        for (Rank rank : rankList){
            if (rank.getName().equalsIgnoreCase(rankName)){
                return rank;
            }
        }
        return null;
    }

    public void removeRank(Player player) {
        playersRank.put(player.getName(), getJoueurRank());
    }

    public Rank getJoueurRank() {
        return rankList.get(rankList.size() - 1); // Le dernier est "Joueur"
    }

}

