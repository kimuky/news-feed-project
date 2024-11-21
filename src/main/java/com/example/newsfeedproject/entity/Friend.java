package com.example.newsfeedproject.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity(name = "friend")
public class Friend {

    @EmbeddedId
    private FriendId id;

    @MapsId("fromUserId")
    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private User fromUserId;

    @MapsId("toUserId")
    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private User toUserId;

    @Column(columnDefinition = "bit")
    private int friendRequest;

    private LocalDateTime relatedAt;

    public Friend(User fromUserId, User toUserId) {
        this.id = new FriendId(fromUserId.getId(), toUserId.getId());
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.friendRequest = 0;
    }

    public Friend(User fromUserId, User toUserId, int friendRequest) {
        this.id = new FriendId(fromUserId.getId(), toUserId.getId());
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.friendRequest = friendRequest;
        this.relatedAt = LocalDateTime.now();
    }

    public Friend() {
    }
}
