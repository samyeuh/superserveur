package com.samy.superserveur.party;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Party {

    private final UUID leaderId;
    private final List<UUID> members;

    public Party(Player leader){
        this.leaderId = leader.getUniqueId();
        this.members = new ArrayList<>();
        this.members.add(leader.getUniqueId());
    }

    public UUID getLeaderId(){
        return leaderId;
    }

    public List<UUID> getMembers(){
        return members;
    }

    public void addMember(Player player){
        members.add(player.getUniqueId());
    }

    public void removeMember(Player player){
        members.remove(player.getUniqueId());
    }

    public boolean isMember(Player player){
        return members.contains(player.getUniqueId());
    }

    public boolean isLeader(Player player){
        return leaderId.equals(player.getUniqueId());
    }
}
