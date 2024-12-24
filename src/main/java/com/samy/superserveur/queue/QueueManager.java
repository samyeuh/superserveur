package com.samy.superserveur.queue;

import com.samy.api.queue.IQueueManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class QueueManager implements IQueueManager {

    public HashMap<Player, String> playersGame = new HashMap<>();
    public Queue<Player> queue = new LinkedList<>();

    public void addPlayer(Player player, String game){
        if (playersGame.containsKey(player) && queue.contains(player)) return;
        queue.add(player);
        playersGame.put(player, game);
        QueueMessageUtils.joiningQueue(player, game);
    }

    public void sendPlayerToGame(Player player){
        String game = playersGame.get(player);
        if(game == null) return;
        player.sendMessage("§aVous avez été envoyé dans la partie " + game);
        playersGame.remove(player);
    }

    public Queue<Player> getQueue() {
        return queue;
    }

    public Player pollNextPlayer(){
        return queue.poll();
    }

    public boolean isQueueEmpty(){
        return queue.isEmpty();
    }

}
