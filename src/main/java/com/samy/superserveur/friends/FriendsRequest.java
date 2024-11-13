package com.samy.superserveur.friends;

import java.util.UUID;
// plus tard pour temps de réponse
public class FriendsRequest {

    private UUID senderId;
    private UUID receiverId;
    private final long requestTime;

    public FriendsRequest(UUID senderId, UUID receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.requestTime = System.currentTimeMillis();
    }

    public UUID getSenderId() {
        return senderId;
    }

    public UUID getReceiverId() {
        return receiverId;
    }

    public long getRequestTime() {
        return requestTime;
    }
}
