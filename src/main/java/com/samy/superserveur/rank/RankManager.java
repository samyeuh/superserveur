package com.samy.superserveur.rank;

import com.samy.api.SuperAPI;
import com.samy.api.rank.IRank;
import com.samy.api.rank.IRankManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import com.samy.api.rank.Permissions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RankManager implements IRankManager {

    private final HashMap<String, IRank> playersRank = new HashMap<>();
    private final List<IRank> rankList;

    public RankManager() {
        Rank joueur = new Rank("Joueur", Permissions.PLAYER, ChatColor.GRAY, "05_player");
        Rank vip = new Rank("VIP", Permissions.VIP, ChatColor.YELLOW, "04_vip");
        Rank vipplus = new Rank("VIP+", Permissions.VIPPLUS, ChatColor.AQUA, "03_vip+");
        Rank modo = new Rank("Modo", Permissions.MODO, ChatColor.RED, "02_mod");
        Rank admin = new Rank("Admin", Permissions.ADMIN, ChatColor.DARK_RED, "01_admin");
        this.rankList = Arrays.asList(admin, modo, vipplus, vip, joueur);
    }

    public List<IRank> getRanks(){
        return rankList;
    }

    @Override
    public void setRank(Player player, IRank iRank) {
        setRank(player, (Rank) iRank);
    }

    public void setRank(Player player, Rank rank) {
        playersRank.put(player.getName(), rank);
        String name = rank.getColorName() + " " + player.getName();
        player.setDisplayName(name);
        player.setPlayerListName(name);
        // TODO: RankMessageUtils
        SuperAPI.getInstance().getScoreboardManager().updateTab(new ArrayList<>(Bukkit.getOnlinePlayers()));

    }

    public IRank getRank(Player player) {
        return playersRank.getOrDefault(player.getName(), getJoueurRank());
    }

    public IRank findRank(String rankName){
        for (IRank rank : rankList){
            if (rank.getName().equalsIgnoreCase(rankName)){
                return rank;
            }
        }
        return null;
    }

    public void removeRank(Player player) {
        playersRank.put(player.getName(), getJoueurRank());
    }

    public IRank getJoueurRank() {
        return rankList.get(rankList.size() - 1); // Le dernier est "Joueur"
    }

}

