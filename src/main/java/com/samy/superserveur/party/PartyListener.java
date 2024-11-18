package com.samy.superserveur.party;

import com.samy.superserveur.friends.FriendsMessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PartyListener implements Listener {

    // TODO: when player join or left game and is in a party, signal other player

    public PartyManager partyManager;

    public PartyListener(PartyManager partyManager){
        this.partyManager = partyManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Party party = partyManager.getParty(player);
        if (party != null) {
            List<UUID> members = new ArrayList<>(party.getMembers());
            members.remove(player.getUniqueId());
            for(UUID member : members){
                Player m = Bukkit.getPlayer(member);
                PartyMessageUtils.memberConnected(m, player);
            }
        }
    }

    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent event){
        Player player = event.getPlayer();
        Party party = partyManager.getParty(player);
        if (party != null) {
            List<UUID> members = new ArrayList<>(party.getMembers());
            members.remove(player.getUniqueId());
            for(UUID member : members){
                Player m = Bukkit.getPlayer(member);
                PartyMessageUtils.memberDisconnected(m, player);
            }
        }
    }
}
