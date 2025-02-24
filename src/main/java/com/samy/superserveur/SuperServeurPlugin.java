package com.samy.superserveur;

import com.samy.superserveur.friends.FriendsCommand;
import com.samy.superserveur.friends.FriendsListener;
import com.samy.superserveur.help.HelpCommand;
import com.samy.superserveur.message.MessageCommand;
import com.samy.superserveur.party.*;
import com.samy.superserveur.rank.RankCommand;
import com.samy.superserveur.rank.RankListener;
import org.bukkit.plugin.java.JavaPlugin;

public class SuperServeurPlugin extends JavaPlugin {

    private SuperServeurAPI api;

    @Override
    public void onEnable() {

        this.api = new SuperServeurAPI(this);

        // Listeners
        getServer().getPluginManager().registerEvents(new RankListener(this), this);
        getServer().getPluginManager().registerEvents(new FriendsListener(this), this);
        getServer().getPluginManager().registerEvents(new PartyListener(this), this);

       // Commands
        this.getCommand("rank").setExecutor(new RankCommand(this));

        FriendsCommand friendsCommand = new FriendsCommand(this);
        this.getCommand("friends").setExecutor(friendsCommand);
        this.getCommand("f").setExecutor(friendsCommand);

        PartyCommand partyCommand = new PartyCommand(this);
        this.getCommand("party").setExecutor(partyCommand);
        this.getCommand("p").setExecutor(partyCommand);

        MessageCommand messageCommand = new MessageCommand(this);
        this.getCommand("m").setExecutor(messageCommand);
        this.getCommand("r").setExecutor(messageCommand);

        this.getCommand("help").setExecutor(new HelpCommand());

        /*
        // Tab completer
        FriendsTabCompleter friendsTabCompleter = new FriendsTabCompleter(this);
        this.getCommand("friends").setTabCompleter(friendsTabCompleter);
        this.getCommand("f").setTabCompleter(friendsTabCompleter);

        PartyTabCompleter partyTabCompleter = new PartyTabCompleter(this);
        this.getCommand("party").setTabCompleter(partyTabCompleter);
        this.getCommand("p").setTabCompleter(partyTabCompleter);

        MessageTabCompleter messageTabCompleter = new MessageTabCompleter();
        this.getCommand("m").setTabCompleter(messageTabCompleter);
        this.getCommand("r").setTabCompleter(messageTabCompleter);
        */

        getLogger().info("Plugin core est activé !");

    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin core désactivé !");
    }

    public SuperServeurAPI getApi(){
        return api;
    }


}