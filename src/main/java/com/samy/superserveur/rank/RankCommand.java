package com.samy.superserveur.rank;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class RankCommand implements CommandExecutor {

    private RankManager rankManager;

    public RankCommand(RankManager rankManager){
        this.rankManager = rankManager;
    }

    public boolean checkPermission(CommandSender sender){
        if (sender instanceof Player){
            Player p = (Player) sender;
            Rank rank = rankManager.getRank(p);
            if (rank == null){
                return false;
            }
            if (rank.hasPermissions(Permissions.ADMIN)){
                return true;
            } else {
                return false;
            }
        } else if (sender instanceof ConsoleCommandSender){
            return true;
        }
        return false;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!checkPermission(commandSender)){
            commandSender.sendMessage("You don't have the permission to execute this command");
            return false;
        }

        if (strings.length < 2 || !RankSubCommand.contains(strings[0])){
            commandSender.sendMessage("Usage: /rank <set|remove|info> <player> <rank>");
            return false;
        }
        RankSubCommand rankSubCommand = RankSubCommand.valueOf(strings[0].toUpperCase());

        switch (rankSubCommand){
            case SET:
                if (strings.length < 3){
                    commandSender.sendMessage("Usage: /rank set <player> <rank>");
                    return false;
                }
                Player playerSet = Bukkit.getPlayer(strings[1]);
                if (playerSet == null){
                    commandSender.sendMessage("Player not found");
                    return false;
                }
                Rank rank = rankManager.findRank(strings[2]);
                if (rank == null){
                    commandSender.sendMessage("Rank not found");
                    return false;
                }
                rankManager.setRank(playerSet, rank);
                commandSender.sendMessage("Rank set");
                break;
            case REMOVE:
                if (strings.length < 3){
                    commandSender.sendMessage("Usage: /rank remove <player> <rank>");
                    return false;
                }
                Player playerRemove = Bukkit.getPlayer(strings[1]);
                if (playerRemove == null){
                    commandSender.sendMessage("Player not found");
                    return false;
                }
                rankManager.removeRank(playerRemove);
                break;
            case INFO:
                Player playerInfo = Bukkit.getPlayer(strings[1]);
                if (playerInfo == null){
                    commandSender.sendMessage("Player not found or not connected");
                    return false;
                }
                Rank rankInfo = rankManager.getRank(playerInfo);
                commandSender.sendMessage("Rank of " + playerInfo.getName() + ": " + rankInfo.getName());
                break;
        }
        return true;
    }
}
