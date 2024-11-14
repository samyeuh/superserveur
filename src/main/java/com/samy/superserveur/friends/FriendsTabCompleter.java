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

            completions = Arrays.asList("add", "delete", "list", "help", "accept");
        } else if (args.length == 2 && (args[1].equals("add")) || (args[1].equals("remove")) || (args[1].equals("accept"))) {
            if (args[1].equals("accept")) {
                Player player = (Player) sender;
                completions = friendsManager.getRequests(player);
            } else {
                completions = getOnlinePlayers();
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
