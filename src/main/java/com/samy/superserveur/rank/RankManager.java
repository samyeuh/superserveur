package com.samy.superserveur.rank;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RankManager {

        private HashMap<String, Rank> playersRank = new HashMap<>();
        private List<Rank> rankList;

        public RankManager(){
            Rank Joueur = new Rank("Joueur", Permissions.PLAYER, ChatColor.GRAY);
            Rank vip = new Rank("VIP", Permissions.VIP, ChatColor.YELLOW);
            Rank vipplus = new Rank("VIP+", Permissions.VIPPLUS, ChatColor.AQUA);
            Rank modo = new Rank("Modo", Permissions.MODO, ChatColor.RED);
            Rank admin = new Rank("Admin", Permissions.ADMIN, ChatColor.DARK_RED);
            this.rankList = Arrays.asList(Joueur, vip, vipplus, modo, admin);
        }

        public Rank findRank(String rankName){
            for (Rank rank : rankList){
                if (rank.getName().equalsIgnoreCase(rankName)){
                    return rank;
                }
            }
            return null;
        }


        public void setRank(Player player, Rank rank){
            playersRank.put(player.getName(), rank);
            setAllName(player);
        }

        public Rank getRank(Player player){
            return playersRank.get(player.getName());
        }

        public void removeRank(Player player){
            playersRank.remove(player.getName());
        }

        public void setAllName(Player player){
            player.setDisplayName(printName(player));
            player.setCustomName(printName(player));
            player.setPlayerListName(printName(player));
        }

        public String printName(Player player){
            Rank rank = getRank(player);
            return rank.getColorName() + " " + player.getName() + ChatColor.RESET;
        }


}
