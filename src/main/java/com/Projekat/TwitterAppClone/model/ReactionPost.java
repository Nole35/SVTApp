package com.Projekat.TwitterAppClone.model;

import com.Projekat.TwitterAppClone.enums.ReactionType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@Table(name = "reaction_post")
public class ReactionPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reaction_id", nullable = false)
    private int reactionId;

    @Column(name = "type")
    private ReactionType type;

    @Column(name = "timestamp", unique = false, nullable = false)
    private LocalDate timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public ReactionPost() {

    }

    public ReactionPost(int reactionId, ReactionType type, LocalDate timestamp, User user, Post post) {
        this.reactionId = reactionId;
        this.type = type;
        this.timestamp = timestamp;
        this.user = user;
        this.post = post;
    }
}
