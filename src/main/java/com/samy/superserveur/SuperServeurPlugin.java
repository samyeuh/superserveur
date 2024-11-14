package com.samy.superserveur;

import com.samy.superserveur.friends.FriendsCommand;
import com.samy.superserveur.friends.FriendsListener;
import com.samy.superserveur.friends.FriendsManager;
import com.samy.superserveur.friends.FriendsTabCompleter;
import com.samy.superserveur.message.MessageCommand;
import com.samy.superserveur.message.MessageManager;
import com.samy.superserveur.message.MessageTabCompleter;
import com.samy.superserveur.party.*;
import org.bukkit.plugin.java.JavaPlugin;

public class SuperServeurPlugin extends JavaPlugin {

    private FriendsManager friendsManager;
    private PartyManager partyManager;
    private MessageManager messageManager;

    @Override
    public void onEnable() {
        enableFriends();
        enableParty();
        enableMessage();
        getLogger().info("SuperServeurPlugin est activé !");
    }

    @Override
    public void onDisable() {
        getLogger().info("SuperServeurPlugin est désactivé.");
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
        partyManager = new PartyManager();
        PartyCommand partyCommand = new PartyCommand(partyManager);
        PartyTabCompleter partyTabCompleter = new PartyTabCompleter(partyManager);

        this.getCommand("party").setExecutor(partyCommand);
        this.getCommand("p").setExecutor(partyCommand);

        this.getCommand("party").setTabCompleter(partyTabCompleter);
        this.getCommand("p").setTabCompleter(partyTabCompleter);
    }

    public void enableMessage(){
        messageManager = new MessageManager();
        MessageCommand messageCommand = new MessageCommand(messageManager);
        MessageTabCompleter messageTabCompleter = new MessageTabCompleter();

        this.getCommand("m").setExecutor(messageCommand);
        this.getCommand("r").setExecutor(messageCommand);

        this.getCommand("m").setTabCompleter(messageTabCompleter);
        this.getCommand("r").setTabCompleter(messageTabCompleter);
    }
}
