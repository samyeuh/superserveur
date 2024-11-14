package com.samy.superserveur.party;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PartyManager {

    private final List<Party> partyList;

    public PartyManager(){
        this.partyList = new ArrayList<>();
    }

    public Party getParty(Player leader){
        for(Party party : partyList){
            if(party.isLeader(leader)){
                return party;
            }
        }
        return null;
    }

    public boolean isMemberOfAParty(Player player){
        for(Party party : partyList){
            if(party.isMember(player) && !party.isLeader(player)){
                return true;
            }
        }
        return false;
    }

    public void addPlayerToParty(Player sender, Player player){
        // ajt le joueur que si il est le leader d'une party
        Party party = getParty(sender);
        if(party != null){
            party.addMember(player);
        } else {
            if (!isMemberOfAParty(player)){
                Party newParty = new Party(sender);
                newParty.addMember(player);
                partyList.add(newParty);
            }
        }
    }

    public void removePlayerFromParty(Player player){
        for(Party party : partyList){
            if(party.isLeader(player)){
                party.removeMember(player);
                if(party.getMembers().size() == 1){
                    partyList.remove(party);
                }
                return;
            }
        }
    }


    public List<String> getPartyMembers(Player sender) {
        Party party = getParty(sender);
        List<String> names = new ArrayList<>();
        if(party != null){
            for (UUID player : party.getMembers()){
                Player p = Bukkit.getPlayer(player);
                names.add(p.getName());
            }
            return names;
        }
        return new ArrayList<>();
    }
}
