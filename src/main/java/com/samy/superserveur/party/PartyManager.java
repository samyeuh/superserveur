package com.samy.superserveur.party;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PartyManager {

    private final List<Party> partyList;
    private final HashMap<UUID, List<Party>> partyRequests = new HashMap<>();

    public PartyManager(){
        this.partyList = new ArrayList<>();
    }

    public boolean isInParty(Player player){
        for(Party party : partyList){
            if(party.isMember(player)){
                return true;
            }
        }
        return false;
    }

    public Party getPartyIfLeader(Player leader){
        for(Party party : partyList){
            if(party.isLeader(leader)){
                return party;
            }
        }
        return null;
    }

    public List<String> getPartyRequestLeader(Player player){
        List<String> names = new ArrayList<>();
        List<Party> requests = partyRequests.getOrDefault(player.getUniqueId(), new ArrayList<>());
        for(Party party : requests){
            names.add(Bukkit.getPlayer(party.getLeaderId()).getName());
        }
        return names;
    }

    public void disbandParty(Player player){
        Party party = getPartyIfLeader(player);
        if(party != null){
            for (UUID member : party.getMembersWithoutLeader()) {
                Player p = Bukkit.getPlayer(member);
                PartyMessageUtils.partyInfo(p, "Le chef du groupe a dissous le groupe.");
            }
            partyList.remove(party);
            PartyMessageUtils.partyInfo(player, "Vous avez dissous le groupe.");
        } else {
            if (isMemberOfAParty(player)){
                PartyMessageUtils.partyError(player, "Vous n'êtes pas le chef du groupe.");
            } else {
                PartyMessageUtils.playerNoParty(player);
            }
        }
    }

    public Party getParty(Player player){
        for(Party party : partyList){
            if(party.isMember(player)){
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

    public void checkPartyForRequest(Player player, Player target){
        if (isMemberOfAParty(player)){
           requestPlayerToParty(player, target);
        } else {
            Party newParty = new Party(player);
            partyList.add(newParty);
            requestPlayerToParty(player, target);
        }
    }

    public void requestPlayerToParty(Player player, Player target){
        Party party = getPartyIfLeader(player);
        if(party != null){
            partyRequests.computeIfAbsent(target.getUniqueId(), k -> new ArrayList<>()).add(party);
            PartyMessageUtils.partyRequest(player, target);
        } else {
            PartyMessageUtils.partyError(player,"Vous n'êtes pas le chef du groupe.");
        }
    }

    public void acceptRequestFromPlayer(Player target, Player sender){
        List<Party> requests = partyRequests.getOrDefault(target.getUniqueId(), new ArrayList<>());
        for(Party party : requests){
            if(party.isLeader(sender)){
                addPlayerToParty(sender, target);
                PartyMessageUtils.playerJoinParty(target, party);
                return;
            }
        }
        PartyMessageUtils.partyInfo(target,"Vous n'avez pas reçu de demande de groupe de la part de " + sender.getName());
    }

    public void acceptMostRecentRequest(Player player){
        List<Party> requests = partyRequests.getOrDefault(player.getUniqueId(), new ArrayList<>());
        if(requests.isEmpty()){
            return;
        }
        Party party = requests.get(requests.size() - 1);
        Player sender = Bukkit.getPlayer(party.getLeaderId());
        acceptRequestFromPlayer(player, sender);
    }

    public void addPlayerToParty(Player sender, Player player){
        Party party = getPartyIfLeader(sender);
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

    public void removePlayerFromParty(Player leader, Player oldFriend){
        for(Party party : partyList){
            if(party.isLeader(leader)){
                party.removeMember(oldFriend);
                PartyMessageUtils.partyInfo(oldFriend, "Vous avez été retiré du groupe.");
                PartyMessageUtils.partyInfo(leader, oldFriend.getName() + " a été retiré du groupe.");
                if(party.getMembers().size() == 1){
                    partyList.remove(party);
                    PartyMessageUtils.partyInfo(leader, "Le groupe a été dissous.");
                }
                return;
            } else {
                PartyMessageUtils.partyError(leader, "Vous n'êtes pas le chef du groupe.");
            }
        }
    }

    public void leaveParty(Player player){
        Party party = getParty(player);
        if(party != null){
            if(party.isLeader(player)){
                PartyMessageUtils.partyInfo(player, "Vous avez quitté le groupe.");
                for (UUID member : party.getMembersWithoutLeader()) {
                    Player p = Bukkit.getPlayer(member);
                    PartyMessageUtils.partyError(p, "Le chef du groupe a quitté le groupe.");
                }
                partyList.remove(party);
            } else {
                party.removeMember(player);
                if (party.getMembers().size() == 1) {
                    partyList.remove(party);
                    Player leader = Bukkit.getPlayer(party.getLeaderId());
                    PartyMessageUtils.partyInfo(leader, "Le groupe a été dissous.");
                }
                PartyMessageUtils.partyInfo(player, "Vous avez quitté le groupe.");
            }
        } else {
            PartyMessageUtils.playerNoParty(player);
        }
    }


    public List<String> getPartyMembers(Player sender) {
        Party party = getParty(sender);
        List<String> names = new ArrayList<>();
        if (party != null && party.getLeaderId() != null){
            Player leader = Bukkit.getPlayer(party.getLeaderId());
            if (leader != null) {
                names.add(leader.getName());
            } else {
                OfflinePlayer offlineLeader = Bukkit.getOfflinePlayer(party.getLeaderId());
                names.add(offlineLeader.getName());
            }
            for (UUID player : party.getMembersWithoutLeader()) {
                Player p = Bukkit.getPlayer(player);
                if (p != null){
                    names.add(p.getName());
                } else {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player);
                    names.add(offlinePlayer.getName());
                }
            }
            return names;
        }
        return new ArrayList<>();
    }

    public void listParty(Player player){
        List<String> partyMembers = getPartyMembers(player);
        if(partyMembers.isEmpty()){
            PartyMessageUtils.playerNoParty(player);
        } else {
            PartyMessageUtils.partyList(player, partyMembers);
        }
    }
}
