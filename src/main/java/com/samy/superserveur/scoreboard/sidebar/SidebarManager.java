package com.samy.superserveur.scoreboard.sidebar;

import com.samy.api.scoreboard.sidebar.ISidebarManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SidebarManager implements ISidebarManager {

    private final Map<Player, List<String>> playerSidebar = new HashMap<>();

    public Scoreboard createSidebar(Scoreboard scoreboard, String title, List<String> lines, Player player) {
        if (scoreboard.getObjective(DisplaySlot.SIDEBAR) != null) {
            scoreboard.getObjective(DisplaySlot.SIDEBAR).unregister();
        }

        playerSidebar.put(player, lines);

        String objName = "obj_" + player.getName() + "_" + System.currentTimeMillis();
        Objective objective = scoreboard.registerNewObjective("custom", objName);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.AQUA + "   " + ChatColor.BOLD + title + "   ");

        setObjective(objective, player);

        return scoreboard;
    }

    public void setObjective(Objective obj, Player player){
        int score = playerSidebar.get(player).size();

        obj.getScore(" ").setScore(score+1);

        for (String line : playerSidebar.get(player)) {
            obj = choseObjective(obj, line, player, score);
            score--;
        }

        obj.getScore("  ").setScore(-1);
        obj.getScore(ChatColor.GOLD + "samy.fr").setScore(-2);
    }

    public void updateSidebarForPlayer(Player player, Scoreboard scoreboard) {
        Objective obj = scoreboard.getObjective(DisplaySlot.SIDEBAR);
        resetScoreboard(scoreboard);
        setObjective(obj, player);
        player.setScoreboard(scoreboard);
    }

    public void resetScoreboard(Scoreboard scoreboard){
        for (String entry : scoreboard.getEntries()) {
                scoreboard.resetScores(entry);
        }
    }

    public Objective choseObjective(Objective obj, String line, Player player, int score){
        switch(line){
            case "playerName":
                String playerObjName = ChatColor.YELLOW + "" + ChatColor.BOLD + "> " + player.getName();
                obj.getScore(playerObjName).setScore(score);
                break;
            case "coins":
                String coinsObjName = ChatColor.BOLD + "Coins: " + ChatColor.GOLD + "0";
                obj.getScore(coinsObjName).setScore(score);
                break;
            case "connectedPlayers":
                String connectedPlayersObjName = ChatColor.BOLD + "Connectés: " + ChatColor.GREEN + Bukkit.getOnlinePlayers().size();
                obj.getScore(connectedPlayersObjName).setScore(score);
                break;
        }
        return obj;
    }
}
