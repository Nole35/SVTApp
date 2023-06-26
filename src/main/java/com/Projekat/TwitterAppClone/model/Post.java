package com.Projekat.TwitterAppClone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@Table(name="post")
public class Post {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private int postId;

    @Column(name = "content")
    private String content;

    @Column(name = "creationDate")
    private LocalDate creationDate;

    @Column(name = "imagePath")
    private String imagePath;

    //BACA GRESKU
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "reaction")
    private Integer reactions;


    @Column(name = "`active`")
    private String active;

    public Post() {

    }


}