package com.samy.superserveur.rank;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class RankListener implements Listener {

    public RankManager rankManager;

    public RankListener(RankManager rankManager){
        this.rankManager = rankManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        Rank rank = rankManager.getRank(p);
        if (rank == null){
            rankManager.setRank(p, rankManager.findRank("Joueur"));
        } else {
            rankManager.setAllName(p);
        }
    }
}
