package com.samy.superserveur.friends;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class FriendsManager {
    private final HashMap<UUID, List<UUID>> friends = new HashMap<>();
    private final HashMap<UUID, List<UUID>> requests = new HashMap<>();

    public void sendRequest(Player sender, Player target) {
        requests.computeIfAbsent(target.getUniqueId(), k -> new ArrayList<>()).add(sender.getUniqueId());
    }

    public boolean acceptRequest(Player player, Player requester){
        List<UUID> requests = this.requests.getOrDefault(player.getUniqueId(), new ArrayList<>());
        if (requests.contains(requester.getUniqueId())) {
            addFriend(player, requester);
            requests.remove(requester.getUniqueId());
            return true;
        } else {
            return false;
        }
    }

    public void acceptMostRecentRequest(Player player) {
        List<UUID> requests = this.requests.getOrDefault(player.getUniqueId(), new ArrayList<>());
        if (requests.isEmpty()) {
            return;
        }
        Player requester = player.getServer().getPlayer(requests.get(requests.size() - 1));
        if (requester != null) {
            acceptRequest(player, requester);
        } else {
            // erreur
            return;
        }
    }

    private void addFriend(Player player, Player friend) {
        friends.computeIfAbsent(player.getUniqueId(), k -> new ArrayList<>()).add(friend.getUniqueId());
        friends.computeIfAbsent(friend.getUniqueId(), k -> new ArrayList<>()).add(player.getUniqueId());
        // send message
    }

    public void removeFriend(Player player, Player friend){
        friends.getOrDefault(player.getUniqueId(), new ArrayList<>()).remove(friend.getUniqueId());
        friends.getOrDefault(friend.getUniqueId(), new ArrayList<>()).remove(player.getUniqueId());
        // message
    }

    public boolean areFriends(Player player, Player friend) {
        return friends.getOrDefault(player.getUniqueId(), new ArrayList<>()).contains(friend.getUniqueId());
    }

    public List<UUID> getFriends(Player player) {
        return friends.getOrDefault(player.getUniqueId(), new ArrayList<>());
    }
}
