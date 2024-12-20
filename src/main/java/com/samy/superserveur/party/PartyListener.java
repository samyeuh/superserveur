package com.samy.superserveur.party;

import com.samy.api.party.IParty;
import com.samy.api.party.IPartyManager;
import com.samy.superserveur.SuperServeurPlugin;
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

    public IPartyManager partyManager;

    public PartyListener(SuperServeurPlugin plugin){
        this.partyManager = plugin.getApi().getPartyManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        IParty party = partyManager.getParty(player);
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
        IParty party = partyManager.getParty(player);
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
