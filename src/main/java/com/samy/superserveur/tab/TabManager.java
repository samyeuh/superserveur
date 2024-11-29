package com.samy.superserveur.tab;

import com.samy.superserveur.rank.Rank;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Arrays;
import java.util.List;

public class TabManager {

    public static List<Team> teams;
    private final Scoreboard scoreboard;
    private final List<Rank> ranks;

    public TabManager(Scoreboard scoreboard, List<Rank> ranks) {
        this.scoreboard = scoreboard;
        this.ranks = ranks;
    }

    public Team createTeam(String teamName, ChatColor color) {
        Team t = scoreboard.registerNewTeam(teamName);
        t.setPrefix(color + " ");
        return t;
    }

    public static void assignPlayerToTeam(Team team, Player player) {
        String pseudo = player.getName();
        for (Team t : teams){
            if (t.hasEntry(pseudo)){
                t.removeEntry(pseudo);
            }
        }
        team.addEntry(pseudo);
    }

    public void createRankTab(){
        Team admin = createTeam("01_admin", ranks.get(0).getColor());
        Team mod = createTeam("02_mod", ranks.get(1).getColor());
        Team vipplus = createTeam("03_vip+", ranks.get(2).getColor());
        Team vip = createTeam("04_vip", ranks.get(3).getColor());
        Team player = createTeam("05_player", ranks.get(4).getColor());
        teams = Arrays.asList(admin, mod, vipplus, vip, player);
    }
}
