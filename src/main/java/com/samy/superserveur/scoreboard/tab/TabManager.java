package com.samy.superserveur.scoreboard.tab;

import com.samy.superserveur.rank.Rank;
import com.samy.superserveur.rank.RankManager;
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

public class TabManager {

    private final RankManager rankManager;
    private final List<Rank> ranks;
    private List<Team> teamsRank;
    private Scoreboard scoreboard;

    public TabManager(RankManager rank) {
        this.ranks = rank.getRanks();
        this.rankManager = rank;
    }

        private void setHeaderAndFooter(Player player){
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
            this.teamsRank = createTeams();
            assignPlayerToTeamRank(player);
            setHeaderAndFooter(player);
            updateTabForPlayer(scoreboard);
            return scoreboard;
        }

        public List<Team> createTeams(){
            return Arrays.asList(
                    createTeamRank(ranks.get(0)),
                    createTeamRank(ranks.get(1)),
                    createTeamRank(ranks.get(2)),
                    createTeamRank(ranks.get(3)),
                    createTeamRank(ranks.get(4))
            );
        }

    public Team createTeamRank(Rank rank) {
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
        Rank rank = rankManager.getRank(player);
        Team team = scoreboard.getTeam(rank.getTeamName());

        for (Team t : teamsRank) {
            t.removeEntry(pseudo);
            if (team != null) {
                team.addEntry(pseudo);
            }
        }
    }



    public void updateTabForPlayer(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
        for (Player player : Bukkit.getOnlinePlayers()) {
            assignPlayerToTeamRank(player);
        }
    }


        // -- Teams tab

//    public void setTeamsGame(List<TeamGame> list) {
//        teamsGame = Collections.emptyList();
//        for (TeamGame team : list) {
//            teamsGame.add(createTeamGame(team));
//        }
//        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
//            player.setScoreboard(scoreboard);
//        }
//    }
//
//    public Team createTeamGame(TeamGame team){
//        Team t = scoreboard.registerNewTeam(team.name);
//        t.setPrefix(team.color + "");
//        for (Player p : team.players) {
//            t.addEntry(p.getName());
//        }
//        return t;
//    }
}
