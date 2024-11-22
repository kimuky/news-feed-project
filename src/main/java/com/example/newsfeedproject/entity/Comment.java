package com.example.newsfeedproject.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String writeComment;

    @Column(nullable = false)
    private int likeCount = 0;

    public Comment() {
    }

    public Comment(Post post, User user, String writeComment) {
        this.post = post;
        this.user = user;
        this.writeComment = writeComment;
    }

    public void update(Post post, String writeComment, User user) {
        this.post = post;
        this.writeComment = writeComment;
        this.user = user;
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        this.likeCount--;
    }
}
