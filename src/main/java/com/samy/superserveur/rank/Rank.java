package com.samy.superserveur.rank;

import org.bukkit.ChatColor;

public class Rank {

    private final String name;
    private final Permissions permissions;
    private final ChatColor color;

    public Rank(String name, Permissions permissions, ChatColor chatColor){
        this.name = name;
        this.permissions = permissions;
        this.color = chatColor;
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

    public boolean hasPermissions(Permissions perm){
        return permissions.ordinal() >= perm.ordinal();
    }
}
