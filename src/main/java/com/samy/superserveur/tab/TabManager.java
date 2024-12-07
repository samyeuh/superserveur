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

    public void setHeaderAndFooter(Player player) {

        player.setPlayerListHeader("" + ChatColor.YELLOW + ChatColor.BOLD + "le super serveur de samy");
        player.setPlayerListFooter("" + ChatColor.GREEN + ChatColor.BOLD + "supersite.fr");
    }

    public Team createTeam(String teamName, Rank rank) {
        Team t = scoreboard.registerNewTeam(teamName);
        t.setColor(rank.getColor());
        return t;
    }

    public static void assignPlayerToTeam(Team team, Player player) {
        String pseudo = player.getName();
        for (Team t : teams) {
            if (t.hasEntry(pseudo)) {
                t.removeEntry(pseudo);
            }
        }
        team.addEntry(pseudo);
        player.setScoreboard(team.getScoreboard());
    }

    public void createRankTab(){
        Team admin = createTeam("01_admin", ranks.get(0));
        Team mod = createTeam("02_mod", ranks.get(1));
        Team vipplus = createTeam("03_vip+", ranks.get(2));
        Team vip = createTeam("04_vip", ranks.get(3));
        Team player = createTeam("05_player", ranks.get(4));
        teams = Arrays.asList(admin, mod, vipplus, vip, player);
    }
}
