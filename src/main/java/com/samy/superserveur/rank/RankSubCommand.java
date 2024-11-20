package com.samy.superserveur.rank;

import com.samy.superserveur.party.PartySubCommand;

public enum RankSubCommand {
    SET,
    REMOVE,
    INFO;

    public static boolean contains(String test) {
        for (RankSubCommand subCommand : RankSubCommand.values()) {
            if (subCommand.name().equalsIgnoreCase(test)) {
                return true;
            }
        }
        return false;
    }
}
