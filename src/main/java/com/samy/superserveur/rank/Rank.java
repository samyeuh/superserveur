package com.samy.superserveur.rank;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Rank {

    private final String name;
    private final List<Permissions> permissionsList;
    private final ChatColor color;

    public Rank(String name, List<String> permissions, String chatColor){
        this.name = name;
        this.permissionsList = setPerms(permissions);
        this.color = ChatColor.valueOf(chatColor);
    }

    public List<Permissions> setPerms(List<String> permissions){
        List<Permissions> perms = new ArrayList<>();
        for(String perm : permissions){
            perms.add(Permissions.valueOf(perm));
        }
        return perms;
    }

    public String getName() {
        return color + name;
    }

    public boolean hasPermissions(Permissions perm){
        return permissionsList.contains(perm) || permissionsList.contains(Permissions.ALL);
    }
}
