package com.example.newsfeedproject.entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class FriendId implements Serializable {

    private Long fromUserId;

    private Long toUserId;

    public FriendId(Long fromUserId, Long toUserId) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }

    public FriendId() {
    }
}
