package com.samy.superserveur;

import com.samy.api.SuperAPI;
import com.samy.api.TeamGame;
import com.samy.superserveur.friends.FriendsCommand;
import com.samy.superserveur.friends.FriendsListener;
import com.samy.superserveur.friends.FriendsManager;
import com.samy.superserveur.friends.FriendsTabCompleter;
import com.samy.superserveur.help.HelpCommand;
import com.samy.superserveur.message.MessageCommand;
import com.samy.superserveur.message.MessageManager;
import com.samy.superserveur.message.MessageTabCompleter;
import com.samy.superserveur.party.*;
import com.samy.superserveur.rank.RankCommand;
import com.samy.superserveur.rank.RankListener;
import com.samy.superserveur.rank.RankManager;
import com.samy.superserveur.scoreboard.ScoreboardListener;
import com.samy.superserveur.scoreboard.ScoreboardManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SuperServeurPlugin extends JavaPlugin implements SuperAPI {

    private RankManager rankManager;
    public FriendsManager friendsManager;
    public ScoreboardManager scoreboardManager;


    @Override
    public void onEnable() {
        enableRanks();
        enableFriends();
        enableParty();
        enableMessage();
        enableHelp();
        enableScoreboard();
        getLogger().info("Plugin core est activé !");

    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin core désactivé !");
    }

    public void enableFriends() {
        friendsManager = new FriendsManager();
        FriendsCommand friendsCommand = new FriendsCommand(friendsManager);
        FriendsTabCompleter friendsTabCompleter = new FriendsTabCompleter(friendsManager);

        this.getCommand("friends").setExecutor(friendsCommand);
        this.getCommand("f").setExecutor(friendsCommand);

        this.getCommand("friends").setTabCompleter(friendsTabCompleter);
        this.getCommand("f").setTabCompleter(friendsTabCompleter);

        getServer().getPluginManager().registerEvents(new FriendsListener(friendsManager), this);
    }

    public void enableParty() {
        PartyManager partyManager = new PartyManager();
        PartyCommand partyCommand = new PartyCommand(partyManager);
        PartyTabCompleter partyTabCompleter = new PartyTabCompleter(partyManager);

        this.getCommand("party").setExecutor(partyCommand);
        this.getCommand("p").setExecutor(partyCommand);

        this.getCommand("party").setTabCompleter(partyTabCompleter);
        this.getCommand("p").setTabCompleter(partyTabCompleter);

        getServer().getPluginManager().registerEvents(new PartyListener(partyManager), this);
    }

    public void enableMessage() {
        MessageManager messageManager = new MessageManager();
        MessageCommand messageCommand = new MessageCommand(messageManager);
        MessageTabCompleter messageTabCompleter = new MessageTabCompleter();

        this.getCommand("m").setExecutor(messageCommand);
        this.getCommand("r").setExecutor(messageCommand);

        this.getCommand("m").setTabCompleter(messageTabCompleter);
        this.getCommand("r").setTabCompleter(messageTabCompleter);
    }

    public void enableHelp() {
        this.getCommand("help").setExecutor(new HelpCommand());
    }

    public void enableRanks() {
        rankManager = new RankManager();
        this.getCommand("rank").setExecutor(new RankCommand(rankManager));
        getServer().getPluginManager().registerEvents(new RankListener(rankManager), this);
    }

    public void enableScoreboard() {
        scoreboardManager = new ScoreboardManager(rankManager);
        getServer().getPluginManager().registerEvents(new ScoreboardListener(this), this);
    }

    public void updateScoreboard(){
        scoreboardManager.updateTab();
        scoreboardManager.updateSidebar();
    }

    @Override
    public Map<UUID, List<UUID>> getFriends() {
        return friendsManager.getFriends();
    }

    @Override
    public void createRankTab() {
        // ..
    }

    @Override
    public void createTeams(List<TeamGame> list) {
        // ..
    }

    @Override
    public void joinTeam(Player player, TeamGame teamGame) {
        // ...
    }

    @Override
    public void setScoreboard(String s, List<String> list, Player player) {
        scoreboardManager.setScoreboard(player, s, list);
    }

}