package com.samy.superserveur.help;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.Objects;

public class HelpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        Player p = (Player) sender;
        HelpMessageUtils.helpStart(p);
        
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            PluginDescriptionFile desc = plugin.getDescription();
            for (String cmd : desc.getCommands().keySet()){
                String description = (String) desc.getCommands().get(cmd).get("description");
                String usage = (String) desc.getCommands().get(cmd).get("usage");
                boolean display = (boolean) desc.getCommands().get(cmd).get("display");
                if (display){
                    HelpMessageUtils.helpCommand(p, cmd, description, usage);
                }

            }
        }
        
        HelpMessageUtils.helpEnd(p);
        return true;
    }
}
