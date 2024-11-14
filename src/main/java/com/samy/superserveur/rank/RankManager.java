package com.samy.superserveur.rank;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class RankManager {

        private HashMap<String, Rank> playersRank = new HashMap<>();

        public void addRank(Player player, Rank rank){
            playersRank.put(player.getName(), rank);
        }
}
