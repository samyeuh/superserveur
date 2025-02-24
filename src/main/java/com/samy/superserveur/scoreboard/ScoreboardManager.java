package com.samy.superserveur.scoreboard;

import com.samy.api.scoreboard.IScoreboardManager;
import com.samy.api.scoreboard.sidebar.ISidebarManager;
import com.samy.api.scoreboard.tab.ITabManager;
import com.samy.superserveur.SuperServeurAPI;
import com.samy.superserveur.scoreboard.sidebar.SidebarManager;
import com.samy.superserveur.scoreboard.tab.TabManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class ScoreboardManager implements IScoreboardManager {

    private final Map<Player, Scoreboard> playerScoreboards = new HashMap<>();
    private final ISidebarManager sidebarManager;
    private final ITabManager tabManager;
    private boolean rankTab = true;

    public ScoreboardManager(){
        this.sidebarManager = new SidebarManager();
        this.tabManager = new TabManager(SuperServeurAPI.getInstance().getRankManager());
    }

    public void setScoreboard(Player player, Objective objective, Map<Player, String> teams) {
        this.rankTab = teams == null;
        Scoreboard scoreboard = playerScoreboards.computeIfAbsent(player, k -> Bukkit.getScoreboardManager().getNewScoreboard());
        scoreboard = sidebarManager.setSidebar(scoreboard, player, objective);
        scoreboard = tabManager.createTabManager(scoreboard, player);
        player.setScoreboard(scoreboard);
    }

    public void updateTab(List<Player> players) {
        if (!rankTab) return;
        for (Map.Entry<Player, Scoreboard> entry : playerScoreboards.entrySet()) {
            Player player = entry.getKey();
            if (players.contains(player))
                tabManager.updateTabForPlayers(entry.getValue());
        }
    }

    @Override
    public ITabManager getTabManager() {
        return tabManager;
    }

    @Override
    public ISidebarManager getSidebarManager() {
        return sidebarManager;
    }
}
