package com.example.newsfeedproject.dto.friend;

import com.example.newsfeedproject.entity.Friend;
import lombok.Getter;

@Getter
public class FriendResponseAllDto {

    private final Long userId;

    public FriendResponseAllDto(Long userId) {
        this.userId = userId;
    }

    public static FriendResponseAllDto toDto (Friend friend) {
        return new FriendResponseAllDto(friend.getFromUserId());
    }
}
