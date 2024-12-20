package com.samy.superserveur.scoreboard.tab;

import com.samy.api.rank.IRank;
import com.samy.api.rank.IRankManager;
import com.samy.api.scoreboard.tab.ITabManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Arrays;
import java.util.List;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

public class TabManager implements ITabManager {

    private final IRankManager rankManager;
    private final List<IRank> ranks;
    private List<Team> teamsRank;
    private Scoreboard scoreboard;

    public TabManager(IRankManager rankManager) {
        this.rankManager = rankManager;
        this.ranks = rankManager.getRanks();
    }

    public void setHeaderAndFooter(Player player){
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
            Bukkit.getLogger().warning(e.getMessage());
            return;
        }

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

        // -- Rank tab
        public Scoreboard createTabManager(Scoreboard scoreboard, Player player){
            this.scoreboard = scoreboard;
            this.teamsRank = createTeams(scoreboard, player);
            assignPlayerToTeamRank(player);
            setHeaderAndFooter(player);
            updateTabForPlayers(scoreboard);
            return scoreboard;
        }

    @Override
    public List<Team> createTeams(Scoreboard scoreboard, Player player) {
        return Arrays.asList(
                createTeamRank(ranks.get(0)),
                createTeamRank(ranks.get(1)),
                createTeamRank(ranks.get(2)),
                createTeamRank(ranks.get(3)),
                createTeamRank(ranks.get(4))
        );
    }

    @Override
    public Team createTeamRank(IRank rank) {
        String teamName = rank.getTeamName();
        Team team = scoreboard.getTeam(teamName);
        if (team == null) {
            team = scoreboard.registerNewTeam(teamName);
            team.setPrefix(rank.getColor() + "");
        }
        return team;
    }

    public void assignPlayerToTeamRank(Player player) {
        String pseudo = player.getName();
        IRank rank = rankManager.getRank(player);
        Team team = scoreboard.getTeam(rank.getTeamName());

        for (Team t : teamsRank) {
            t.removeEntry(pseudo);
            if (team != null) {
                team.addEntry(pseudo);
            }
        }
    }

    @Override
    public void updateTabForPlayers(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
        for (Player player : Bukkit.getOnlinePlayers()) {
            assignPlayerToTeamRank(player);
        }
    }
}
