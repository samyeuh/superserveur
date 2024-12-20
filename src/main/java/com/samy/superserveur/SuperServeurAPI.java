package com.samy.superserveur;

import com.samy.api.SuperAPI;
import com.samy.api.friends.IFriendsManager;
import com.samy.api.message.IMessageManager;
import com.samy.api.party.IPartyManager;
import com.samy.api.rank.IRankManager;
import com.samy.api.scoreboard.IScoreboardManager;
import com.samy.superserveur.friends.FriendsManager;
import com.samy.superserveur.message.MessageManager;
import com.samy.superserveur.party.PartyManager;
import com.samy.superserveur.rank.RankManager;
import com.samy.superserveur.scoreboard.ScoreboardManager;

public class SuperServeurAPI extends SuperAPI {

    private static SuperServeurAPI instance;
    private final RankManager rankManager;
    private final FriendsManager friendsManager;
    private final ScoreboardManager scoreboardManager;
    private final PartyManager partyManager;
    private final MessageManager messageManager;


    public SuperServeurAPI(SuperServeurPlugin plugin){
        super(plugin);
        instance = this;

        this.rankManager = new RankManager();
        this.friendsManager = new FriendsManager();
        this.partyManager = new PartyManager();
        this.messageManager = new MessageManager();
        this.scoreboardManager = new ScoreboardManager();
    }

    public static SuperServeurAPI getInstance() {
        return instance;
    }

    @Override
    public String getServerName() {
        return "";
    }

    @Override
    public IFriendsManager getFriendsManager() {
        return friendsManager;
    }

    @Override
    public IMessageManager getMessageManager() {
        return messageManager;
    }

    @Override
    public IPartyManager getPartyManager() {
        return partyManager;
    }

    @Override
    public IRankManager getRankManager() {
        return rankManager;
    }

    @Override
    public IScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }
}
