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

        if (args.length == 1) {
            Arrays.stream(FriendsSubCommand.values())
                    .map(subCommand -> subCommand.name().toLowerCase())
                    .forEach(completions::add);
        } else if (args.length == 2) {
            String subCommand = args[0].toLowerCase();
            if (Arrays.asList("accept").contains(subCommand)) {
                Player player = (Player) sender;
                completions = friendsManager.getRequests(player);
            } else if (Arrays.asList("remove", "delete").contains(subCommand)) {
                completions = friendsManager.getFriendsName((Player) sender);
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
