package com.samy.superserveur.party;

public enum PartySubCommand {

    ADD,
    ACCEPT,
    LIST,
    REMOVE,
    HELP;

    public static boolean contains(String test) {
        for (PartySubCommand subCommand : PartySubCommand.values()) {
            if (subCommand.name().equalsIgnoreCase(test)) {
                return true;
            }
        }
        return false;
    }
}
