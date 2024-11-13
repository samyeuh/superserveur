package com.samy.superserveur;

import com.samy.superserveur.friends.FriendsCommand;
import com.samy.superserveur.friends.FriendsListener;
import com.samy.superserveur.friends.FriendsManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SuperServeurPlugin extends JavaPlugin {

    private FriendsManager friendsManager;

    @Override
    public void onEnable() {
        friendsManager = new FriendsManager();
        this.getCommand("friends").setExecutor(new FriendsCommand(friendsManager));
        getServer().getPluginManager().registerEvents(new FriendsListener(friendsManager), this);
        getLogger().info("SuperServeurPlugin est activé !");
    }

    @Override
    public void onDisable() {
        getLogger().info("SuperServeurPlugin est désactivé.");
    }
}
