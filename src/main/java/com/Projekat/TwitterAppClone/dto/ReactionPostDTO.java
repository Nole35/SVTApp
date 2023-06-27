package com.Projekat.TwitterAppClone.dto;

import com.Projekat.TwitterAppClone.enums.ReactionType;
import com.Projekat.TwitterAppClone.model.ReactionPost;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ReactionPostDTO implements Serializable {
    private int reactionId;
    private ReactionType reactionType;
    private LocalDate timestamp;
    private int userId;
    private int postId;

    public ReactionPostDTO(ReactionPost reactionPost) {
        this.reactionId = reactionPost.getReactionId();
        this.reactionType = reactionPost.getType();
        this.timestamp = reactionPost.getTimestamp();
        this.userId = reactionPost.getUser().getUserId();
        this.postId = reactionPost.getPost().getPostId();
    }

}
