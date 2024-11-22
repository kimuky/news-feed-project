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
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String writeComment;

    public Comment() {
    }

    public Comment(String writeComment, User user) {
        this.writeComment = writeComment;
        this.user = user;
    }

    public void update(String writeComment, User user) {
        this.writeComment = writeComment;
        this.user = user;
    }
}
