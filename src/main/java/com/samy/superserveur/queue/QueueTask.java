package com.samy.superserveur.queue;

import com.samy.api.queue.IQueueManager;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class QueueTask extends BukkitRunnable {

    private final IQueueManager queueManager;

    public QueueTask(IQueueManager queueManager){
        this.queueManager = queueManager;
    }

    @Override
    public void run() {
        if(queueManager.isQueueEmpty()) return;
        Player player = queueManager.pollNextPlayer();

        if (player == null) return;
        queueManager.sendPlayerToGame(player);
    }
}
