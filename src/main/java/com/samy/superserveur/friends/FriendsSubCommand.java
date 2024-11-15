package com.samy.superserveur.friends;

public enum FriendsSubCommand {
    ADD,
    INVIT,
    LIST,
    DELETE,
    REMOVE,
    HELP,
    ACCEPT,
    JOIN;

    public static boolean contains(String test) {
        for (FriendsSubCommand subCommand : FriendsSubCommand.values()) {
            if (subCommand.name().equalsIgnoreCase(test)) {
                return true;
            }
        }
        return false;
    }

}
