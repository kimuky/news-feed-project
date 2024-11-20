package com.example.newsfeedproject.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Friend {

    @Id
    private Long fromUserId;
    private LocalDateTime relatedAt;

    // 0, 1
    // 0: 친구 수락 대기
    // 1: 친구 상태
    @Column(columnDefinition = "bit")
    private int friendRequest;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private User user;

    public void setUser (User user ) {
        this.user = user;
    }

    public Friend() {
    }
}
