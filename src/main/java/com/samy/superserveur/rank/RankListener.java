package com.samy.superserveur.rank;

import com.samy.api.rank.IRank;
import com.samy.api.rank.IRankManager;
import com.samy.superserveur.SuperServeurPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class RankListener implements Listener {

    public IRankManager rankManager;

    public RankListener(SuperServeurPlugin plugin){
        this.rankManager = plugin.getApi().getRankManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        IRank rank = rankManager.getRank(p);
        if (rank == null){
            rankManager.setRank(p, rankManager.getJoueurRank());
        } else {
            rankManager.setRank(p, rank);
        }
    }
}
