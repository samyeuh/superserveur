package com.samy.superserveur.scoreboard;

import com.samy.superserveur.rank.RankManager;
import com.samy.superserveur.scoreboard.sidebar.SidebarManager;
import com.samy.superserveur.scoreboard.tab.TabManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreboardManager {

    private final Map<Player, Scoreboard> playerScoreboards = new HashMap<>();
    private final SidebarManager sidebarManager;
    private final TabManager tabManager;

    public ScoreboardManager(RankManager rank){
        this.sidebarManager = new SidebarManager();
        this.tabManager = new TabManager(rank);
    }

    public void setScoreboard(Player player, String title, List<String> lines) {
        Scoreboard scoreboard = playerScoreboards.computeIfAbsent(player, k -> Bukkit.getScoreboardManager().getNewScoreboard());
        scoreboard = sidebarManager.createSidebar(scoreboard, title, lines, player);
        scoreboard = tabManager.createTabManager(scoreboard, player);
        player.setScoreboard(scoreboard);
    }

    public void updateTab() {
        for (Map.Entry<Player, Scoreboard> entry : playerScoreboards.entrySet()) {
            tabManager.updateTabForPlayer(entry.getValue());
        }
    }

    public void updateSidebar(){
        for (Map.Entry<Player, Scoreboard> entry : playerScoreboards.entrySet()) {
            sidebarManager.updateSidebarForPlayer(entry.getKey(), entry.getValue());
        }
    }
}
