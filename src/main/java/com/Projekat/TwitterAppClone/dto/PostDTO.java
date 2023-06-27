package com.Projekat.TwitterAppClone.dto;

import com.Projekat.TwitterAppClone.model.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO implements Serializable {

    private int postId;
    private String content;
    private LocalDate creationDate;
    private String imagePath;
    private int group;
    private int user;
    private String active;

    public PostDTO(Post createdPost) {
        this.postId = createdPost.getPostId();
        this.content = createdPost.getContent();
        this.creationDate = createdPost.getCreationDate();
        this.imagePath = createdPost.getImagePath();
        this.group = createdPost.getGroup().getGroupId();
        this.user = createdPost.getUser().getUserId();
        this.active = createdPost.getActive();

    }

}
