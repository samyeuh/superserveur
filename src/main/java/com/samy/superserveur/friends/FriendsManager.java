package com.samy.superserveur.friends;

import com.samy.api.friends.IFriendsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class FriendsManager implements IFriendsManager {
    private final HashMap<UUID, List<UUID>> friends = new HashMap<>();
    private final HashMap<UUID, List<UUID>> requests = new HashMap<>();

    public void sendRequest(Player sender, Player target) {
        requests.computeIfAbsent(target.getUniqueId(), k -> new ArrayList<>()).add(sender.getUniqueId());
        FriendsMessageUtils.friendRequestSent(sender, target);
    }

    public void acceptRequest(Player player, Player requester){
        List<UUID> requests = this.requests.getOrDefault(player.getUniqueId(), new ArrayList<>());
        if (requests.contains(requester.getUniqueId())) {
            FriendsMessageUtils.friendRequestAccepted(player, requester);
            addFriend(player, requester);
            requests.remove(requester.getUniqueId());
        } else {
            FriendsMessageUtils.friendRequestNotExist(player, requester.getName());
        }
    }

    @Override
    public void acceptMostRequest(Player player) {
        this.acceptMostRecentRequest(player); // TODO a corriger aussi
    }

    public void acceptMostRecentRequest(Player player) {
        List<UUID> requests = this.requests.getOrDefault(player.getUniqueId(), new ArrayList<>());
        if (requests.isEmpty()) {
            return;
        }
        Player requester = player.getServer().getPlayer(requests.get(requests.size() - 1));
        if (requester != null) {
            acceptRequest(player, requester);
        }
    }

    public void addFriend(Player player, Player friend) {
        friends.computeIfAbsent(player.getUniqueId(), k -> new ArrayList<>()).add(friend.getUniqueId());
        friends.computeIfAbsent(friend.getUniqueId(), k -> new ArrayList<>()).add(player.getUniqueId());
        // send message
    }

    public void removeFriend(Player player, Player friend){
        friends.getOrDefault(player.getUniqueId(), new ArrayList<>()).remove(friend.getUniqueId());
        friends.getOrDefault(friend.getUniqueId(), new ArrayList<>()).remove(player.getUniqueId());
        FriendsMessageUtils.friendRemoved(player, friend.getName());
    }

    public boolean areFriends(Player player, Player friend) {
        return friends.getOrDefault(player.getUniqueId(), new ArrayList<>()).contains(friend.getUniqueId());
    }

    public List<UUID> getFriends(Player player) {
        return friends.getOrDefault(player.getUniqueId(), new ArrayList<>());
    }

    public List<String> getFriendsName(Player player){
        List<UUID> friends = getFriends(player);
        List<String> names = new ArrayList<>();
        for(UUID friend : friends){
            Player p = Bukkit.getPlayer(friend);
            names.add(p.getName());
        }
        return names;
    }

    public List<String> getOnlineFriends(Player player) {
        List<UUID> friends = getFriends(player);
        List<String> names = new ArrayList<>();
        for (UUID friend : friends) {
            Player p = Bukkit.getPlayer(friend);
            if (p != null && p.isOnline()) {
                names.add(p.getName());
            }
        }
        return names;
    }

    public List<String> getOfflineFriends(Player player){
        List<UUID> friends = getFriends(player);
        List<String> names = new ArrayList<>();
        for(UUID friend : friends){
            Player p = Bukkit.getPlayer(friend);
            if(p == null || !p.isOnline()){
                Player offlineP = Bukkit.getOfflinePlayer(friend).getPlayer();
                names.add(offlineP.getName());
            }
        }
        return names;
    }

    public List<String> getRequests(Player player) {
        List<UUID> req = requests.getOrDefault(player.getUniqueId(), new ArrayList<>());
        List<String> names = new ArrayList<>();
        for (UUID uuid : req) {
            Player p = Bukkit.getPlayer(uuid);
            names.add(p.getName());
        }

        return names;
    }

    @Override
    public Map<UUID, List<UUID>> getFriendsMap() {
        return friends;
    }

    /*public Map<UUID, List<UUID>> getFriends(){
        return friends;
    }*/
}
