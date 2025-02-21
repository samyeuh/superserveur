package com.samy.superserveur.scoreboard.sidebar;

import com.samy.api.scoreboard.sidebar.ISidebarManager;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;


public class SidebarManager implements ISidebarManager {

    public Scoreboard setSidebar(Scoreboard scoreboard, Player player, Objective objective) {
        Objective oldObjective = scoreboard.getObjective(DisplaySlot.SIDEBAR);

        if (oldObjective != null) {
            oldObjective.unregister();
        }

        Objective newObjective = scoreboard.registerNewObjective("custom", objective.getName());
        newObjective.setDisplayName(objective.getDisplayName());
        newObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

        for (String entry : objective.getScoreboard().getEntries()) {
            int score = objective.getScore(entry).getScore();
            newObjective.getScore(entry).setScore(score);
        }

        return scoreboard;
    }
}
