package com.samy.superserveur.party;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PartyListener implements Listener {

    @EventHandler
    public void onLeaderJoin(PlayerJoinEvent event) {
        //TODO: quand un joueur rejoint un jeu et qu'il est le leader, tout le monde se tp, sinon il se tp seul
    }
}
