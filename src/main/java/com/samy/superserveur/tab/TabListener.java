package com.samy.superserveur.tab;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TabListener implements Listener {

    public TabManager tabManager;

    public TabListener(TabManager tabManager){
        this.tabManager = tabManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        tabManager.setHeaderAndFooter(p);
    }
}
