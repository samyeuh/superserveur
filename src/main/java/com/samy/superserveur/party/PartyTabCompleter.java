package com.samy.superserveur.party;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PartyTabCompleter implements TabCompleter {

    private final PartyManager partyManager;

    public PartyTabCompleter(PartyManager partyManager){
        this.partyManager = partyManager;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions = Arrays.asList("add", "remove", "list", "help", "accept");
        } else if (args.length == 2 && (args[0].equals("add")) || (args[0].equals("remove")) || (args[0].equals("accept"))) {
            if (args[0].equals("remove")){
                Player p = (Player) sender;
                completions = partyManager.getPartyMembers(p);
                completions.remove(sender.getName());
            } else if (args[0].equals("accept")) {
                completions = partyManager.getPartyRequestLeader((Player) sender);
            } else {
                completions = getOnlinePlayers();
                completions.remove(sender.getName());
            }
        }

        return completions;
    }

    // Exemple de méthode pour récupérer les joueurs en ligne
    private List<String> getOnlinePlayers() {
        List<String> playerNames = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(player -> playerNames.add(player.getName()));
        return playerNames;
    }
}
