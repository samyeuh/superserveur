package com.samy.superserveur.friends;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class FriendsListener implements Listener {

    private final FriendsManager friendsManager;

    public FriendsListener(FriendsManager friendsManager){
        this.friendsManager = friendsManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        for (UUID friendId : friendsManager.getFriends(player)) {
            Player friend = Bukkit.getPlayer(friendId);
            if (friend != null) {
                FriendsMessageUtils.friendConnected(friend, player.getName());
            }
        }
    }

    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        for (UUID friendId : friendsManager.getFriends(player)) {
            Player friend = Bukkit.getPlayer(friendId);
            if (friend != null) {
                FriendsMessageUtils.friendDisconnected(friend, player.getName());
            }
        }
    }
}
