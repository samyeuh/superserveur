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
        List<PartySubCommand> subCommands = new ArrayList<>();
        List<PartySubCommand> memberCommands = Arrays.asList(PartySubCommand.QUIT, PartySubCommand.LIST, PartySubCommand.LEAVE);
        List<PartySubCommand> leaderCommands = Arrays.asList(PartySubCommand.DISBAND, PartySubCommand.REMOVE, PartySubCommand.DELETE, PartySubCommand.ADD, PartySubCommand.INVITE, PartySubCommand.REMOVE);
        List<PartySubCommand> noPartyCommands = Arrays.asList(PartySubCommand.ADD, PartySubCommand.ACCEPT, PartySubCommand.INVITE);
        subCommands.add(PartySubCommand.HELP);

        Player player = (Player) sender;


        if (partyManager.isMemberOfAParty(player)) {
            subCommands.addAll(memberCommands);
        } else if (partyManager.getPartyIfLeader(player) != null) {
            subCommands.addAll(leaderCommands);
            subCommands.addAll(memberCommands);
        } else {
            subCommands.addAll(noPartyCommands);
        }

        if (args.length == 1) {
            Arrays.stream(PartySubCommand.values())
                    .filter(subCommands::contains)
                    .map(subCommand -> subCommand.name().toLowerCase())
                    .forEach(completions::add);
        } else if (args.length == 2) {
            String subCommand = args[0].toLowerCase();
            if (Arrays.asList("remove", "delete").contains(subCommand)) {
                completions = partyManager.getPartyMembers(player);
                completions.remove(player.getName());
            } else if (args[0].equals("accept")) {
                completions = partyManager.getPartyRequestLeader(player);
            } else if (Arrays.asList("add", "invite").contains(subCommand)) {
                completions = getOnlinePlayers();
                completions.remove(player.getName());
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
