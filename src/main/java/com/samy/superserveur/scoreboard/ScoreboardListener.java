package com.samy.superserveur.scoreboard;

import com.samy.superserveur.SuperServeurAPI;
import com.samy.superserveur.SuperServeurPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreboardListener implements Listener {

    private final SuperServeurPlugin plugin;
    private final SuperServeurAPI api;

    public ScoreboardListener(SuperServeurPlugin plugin){
        this.plugin = plugin;
        this.api = plugin.getApi();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        api.getScoreboardManager().updateScoreboard();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Bukkit.getScheduler().runTaskLater(plugin, plugin.getApi().getScoreboardManager()::updateScoreboard, 1L);


    }
}
