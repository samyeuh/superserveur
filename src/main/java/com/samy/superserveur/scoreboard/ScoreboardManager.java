package com.samy.superserveur.scoreboard;

import com.samy.api.scoreboard.IScoreboardManager;
import com.samy.api.scoreboard.sidebar.ISidebarManager;
import com.samy.api.scoreboard.tab.ITabManager;
import com.samy.superserveur.SuperServeurAPI;
import com.samy.superserveur.scoreboard.sidebar.SidebarManager;
import com.samy.superserveur.scoreboard.tab.TabManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreboardManager implements IScoreboardManager {

    private final Map<Player, Scoreboard> playerScoreboards = new HashMap<>();
    private final ISidebarManager sidebarManager;
    private final ITabManager tabManager;

    public ScoreboardManager(){
        this.sidebarManager = new SidebarManager();
        this.tabManager = new TabManager(SuperServeurAPI.getInstance().getRankManager());
    }

    public void setScoreboard(Player player, String title, List<String> lines) {
        Scoreboard scoreboard = playerScoreboards.computeIfAbsent(player, k -> Bukkit.getScoreboardManager().getNewScoreboard());
        scoreboard = sidebarManager.createSidebar(scoreboard, title, lines, player);
        scoreboard = tabManager.createTabManager(scoreboard, player);
        player.setScoreboard(scoreboard);
    }

    public void updateScoreboard(){
        this.updateTab();
        this.updateSidebar();
    }

    public void updateTab() {
        for (Map.Entry<Player, Scoreboard> entry : playerScoreboards.entrySet()) {
            tabManager.updateTabForPlayers(entry.getValue());
        }
    }

    public void updateSidebar(){
        for (Map.Entry<Player, Scoreboard> entry : playerScoreboards.entrySet()) {
            sidebarManager.updateSidebarForPlayer(entry.getKey(), entry.getValue());
        }
    }

    public void removePlayer(Player player){
        sidebarManager.resetScoreboard(playerScoreboards.get(player));
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
