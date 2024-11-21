package com.example.newsfeedproject.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class FriendId implements Serializable {
    private Long fromUserId;
    private Long toUserId;

    public FriendId(Long id, Long id1) {
        this.fromUserId = id;
        this.toUserId = id1;
    }

    public FriendId() {

    }
}
