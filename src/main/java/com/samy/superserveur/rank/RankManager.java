package com.samy.superserveur.rank;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.samy.superserveur.tab.TabManager.assignPlayerToTeamRank;
import static com.samy.superserveur.tab.TabManager.teams;

public class RankManager {

        private final HashMap<String, Rank> playersRank = new HashMap<>();
        private final List<Rank> rankList;
        private final Scoreboard scoreboard;

        public RankManager(Scoreboard scoreboard){
            Rank Joueur = new Rank("Joueur", Permissions.PLAYER, ChatColor.GRAY);
            Rank vip = new Rank("VIP", Permissions.VIP, ChatColor.YELLOW);
            Rank vipplus = new Rank("VIP+", Permissions.VIPPLUS, ChatColor.AQUA);
            Rank modo = new Rank("Modo", Permissions.MODO, ChatColor.RED);
            Rank admin = new Rank("Admin", Permissions.ADMIN, ChatColor.DARK_RED);
            this.rankList = Arrays.asList(admin, modo, vipplus, vip, Joueur);
            this.scoreboard = scoreboard;
        }

        public List<Rank> getRanks(){
            return rankList;
        }

        public Rank findRank(String rankName){
            for (Rank rank : rankList){
                if (rank.getName().equalsIgnoreCase(rankName)){
                    return rank;
                }
            }
            return null;
        }

        public Rank getJoueurRank(){
            return rankList.get(4);
        }


        public void setRank(Player player, Rank rank){
            playersRank.put(player.getName(), rank);
            setUpRankTab();
            updateTab();
            setAllName(player);
        }

        public void updateTab(){
            for (Player p : Bukkit.getOnlinePlayers()){
                p.setScoreboard(scoreboard);
            }
        }

        public Rank getRank(Player player){
            return playersRank.get(player.getName());
        }

        public void removeRank(Player player){
            playersRank.remove(player.getName());
        }

        public void setAllName(Player player){
            player.setDisplayName(printRankName(player));
            player.setPlayerListName(printRankName(player));
        }

        public String printRankName(Player player){
            Rank rank = getRank(player);
            return rank.getColorName() + " " + player.getName() + ChatColor.RESET;
        }

    public void setUpRankTab() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Rank rank = getRank(p);
            if (rank == null) rank = getJoueurRank();
            switch (rank.getName().toLowerCase()) {
                case "admin":
                    assignPlayerToTeamRank(teams.get(0), p);
                    break;
                case "modo":
                    assignPlayerToTeamRank(teams.get(1), p);
                    break;
                case "vip+":
                    assignPlayerToTeamRank(teams.get(2), p);
                    break;
                case "vip":
                    assignPlayerToTeamRank(teams.get(3), p);
                    break;
                default:
                    assignPlayerToTeamRank(teams.get(4), p);
                    break;
            }
        }
    }


}
