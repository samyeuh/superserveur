package com.samy.superserveur.tab;

import com.samy.superserveur.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import com.samy.api.TeamGame;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

public class TabManager {

    public static List<Team> teams;
    private List<Team> teamsGame;
    private final Scoreboard scoreboard;
    private final List<Rank> ranks;

    public TabManager(Scoreboard scoreboard, List<Rank> ranks) {
        this.scoreboard = scoreboard;
        this.ranks = ranks;
    }

    public void setHeaderAndFooter(Player player) {
        String header = ChatColor.YELLOW + "" + ChatColor.BOLD + "Le Super Serveur de Samy";
        String footer = ChatColor.GREEN + "" + ChatColor.BOLD + "Visitez : supersite.fr";

        IChatBaseComponent headerComponent = new ChatComponentText(header);
        IChatBaseComponent footerComponent = new ChatComponentText(footer);

        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        try {
            java.lang.reflect.Field headerField = packet.getClass().getDeclaredField("a");
            headerField.setAccessible(true);
            headerField.set(packet, headerComponent);

            java.lang.reflect.Field footerField = packet.getClass().getDeclaredField("b");
            footerField.setAccessible(true);
            footerField.set(packet, footerComponent);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    // -- Rank tab

    public void createRankTab(){
        Team admin = createTeam("01_admin", ranks.get(0));
        Team mod = createTeam("02_mod", ranks.get(1));
        Team vipplus = createTeam("03_vip+", ranks.get(2));
        Team vip = createTeam("04_vip", ranks.get(3));
        Team player = createTeam("05_player", ranks.get(4));
        teams = Arrays.asList(admin, mod, vipplus, vip, player);
    }

    public Team createTeam(String teamName, Rank rank) {
        Team t = scoreboard.registerNewTeam(teamName);
        t.setPrefix(rank.getColor() + "");
        return t;
    }

    public static void assignPlayerToTeamRank(Team team, Player player) {
        String pseudo = player.getName();
        for (Team t : teams) {
            if (t.hasEntry(pseudo)) {
                t.removeEntry(pseudo);
            }
        }
        team.addEntry(pseudo);
        player.setScoreboard(team.getScoreboard());
    }

    // -- Teams tab

    public void setTeamsGame(List<TeamGame> list) {
        teamsGame = Collections.emptyList();
        for (TeamGame team : list) {
            teamsGame.add(createTeamGame(team));
        }
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.setScoreboard(scoreboard);
        }
    }

    public Team createTeamGame(TeamGame team){
        Team t = scoreboard.registerNewTeam(team.name);
        t.setPrefix(team.color + "");
        for (Player p : team.players) {
            t.addEntry(p.getName());
        }
        return t;
    }
}
