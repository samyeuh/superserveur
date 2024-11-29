package com.samy.superserveur;

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
import com.samy.superserveur.tab.TabManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

public class SuperServeurPlugin extends JavaPlugin {

    private RankManager rankManager;
    private Scoreboard scoreboard;

    @Override
    public void onEnable() {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        enableRanks();
        enableTab();
        enableFriends();
        enableParty();
        enableMessage();
        enableHelp();


        getLogger().info("SuperServeurPlugin est activé !");


    }

    @Override
    public void onDisable() {
        getLogger().info("SuperServeurPlugin est désactivé.");
    }

    public void enableFriends() {
        FriendsManager friendsManager = new FriendsManager();
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

    public void enableMessage(){
        MessageManager messageManager = new MessageManager();
        MessageCommand messageCommand = new MessageCommand(messageManager);
        MessageTabCompleter messageTabCompleter = new MessageTabCompleter();

        this.getCommand("m").setExecutor(messageCommand);
        this.getCommand("r").setExecutor(messageCommand);

        this.getCommand("m").setTabCompleter(messageTabCompleter);
        this.getCommand("r").setTabCompleter(messageTabCompleter);
    }

    public void enableHelp(){
        this.getCommand("help").setExecutor(new HelpCommand());
    }

    public void enableRanks(){
        rankManager = new RankManager(scoreboard);
        this.getCommand("rank").setExecutor(new RankCommand(rankManager));
        getServer().getPluginManager().registerEvents(new RankListener(rankManager), this);
    }

    public void enableTab(){
        TabManager tabManager = new TabManager(scoreboard, rankManager.getRanks());
        tabManager.createRankTab();
    }
}