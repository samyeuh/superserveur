package com.samy.superserveur.rank;

import org.bukkit.ChatColor;

public class Rank {

    private final String name;
    private final Permissions permissions;
    private final ChatColor color;
    private final String teamName;

    public Rank(String name, Permissions permissions, ChatColor chatColor, String teamName){
        this.name = name;
        this.permissions = permissions;
        this.color = chatColor;
        this.teamName = teamName;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public String getColorName(){
        return color + name;
    }

    public String getTeamName(){
        return teamName;
    }

    public boolean hasPermissions(Permissions perm){
        return permissions.ordinal() >= perm.ordinal();
    }
}
