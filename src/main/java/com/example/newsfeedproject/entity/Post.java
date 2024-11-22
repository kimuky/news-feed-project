package com.example.newsfeedproject.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "Text")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private int likeCount = 0;

    public void setUser (User user ) {
        this.user = user;
    }

    public Post() {
    }

    public Post(User user,String title, String content ) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void update(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        this.likeCount--;
    }
}
