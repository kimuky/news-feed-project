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
}
