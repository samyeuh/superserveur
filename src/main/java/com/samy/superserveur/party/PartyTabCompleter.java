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
            Arrays.stream(PartySubCommand.values())
                    .map(subCommand -> subCommand.name().toLowerCase())
                    .forEach(completions::add);
        } else if (args.length == 2) {
            String subCommand = args[0].toLowerCase();
            if (Arrays.asList("remove", "delete").contains(subCommand)) {
                Player p = (Player) sender;
                completions = partyManager.getPartyMembers(p);
                completions.remove(sender.getName());
            } else if (args[0].equals("accept")) {
                completions = partyManager.getPartyRequestLeader((Player) sender);
            } else if (Arrays.asList("add", "invit").contains(subCommand)) {
                completions = getOnlinePlayers();
                completions.remove(sender.getName());
            }
        }
        return completions;
    }

    private List<String> getOnlinePlayers() {
        List<String> playerNames = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(player -> playerNames.add(player.getName()));
        return playerNames;
    }
}
