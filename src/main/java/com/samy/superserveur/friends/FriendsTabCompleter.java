package com.samy.superserveur.friends;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FriendsTabCompleter implements TabCompleter {

    private final FriendsManager friendsManager;

    public FriendsTabCompleter(FriendsManager friendsManager) {
        this.friendsManager = friendsManager;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        List<FriendsSubCommand> noFriendCommands = Arrays.asList(FriendsSubCommand.ADD, FriendsSubCommand.INVITE, FriendsSubCommand.ACCEPT, FriendsSubCommand.HELP);
        List<FriendsSubCommand> friendCommands = Arrays.asList(FriendsSubCommand.REMOVE, FriendsSubCommand.DELETE, FriendsSubCommand.JOIN, FriendsSubCommand.LIST);
        List<FriendsSubCommand> subCommands = new ArrayList<>(noFriendCommands);

        Player p = (Player) sender;
        if (!friendsManager.getFriends(p).isEmpty()) {
            subCommands.addAll(friendCommands);
        }

        if (args.length == 1) {
            Arrays.stream(FriendsSubCommand.values())
                    .filter(subCommands::contains)
                    .map(subCommand -> subCommand.name().toLowerCase())
                    .forEach(completions::add);
        } else if (args.length == 2) {
            String subCommand = args[0].toLowerCase();
            if (Arrays.asList("accept").contains(subCommand)) {
                completions = friendsManager.getRequests(p);
            } else if (Arrays.asList("remove", "delete").contains(subCommand)) {
                completions = friendsManager.getFriendsName(p);
            } else if (Arrays.asList("add", "invite").contains(subCommand)) {
                completions = getOnlinePlayers();
                completions.remove(p.getName());
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
