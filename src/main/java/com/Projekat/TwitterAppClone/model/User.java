package com.Projekat.TwitterAppClone.model;

import com.Projekat.TwitterAppClone.enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "username", unique = false, nullable = false)
    private String username;

    @Column(name = "password", unique = false, nullable = false)
    private String password;

    @Column(name = "email", unique = false, nullable = false)
    private String email;

    @Column(name = "registrationDate", unique = false, nullable = false)
    private LocalDate registrationDate;

    @Column(name = "firstName", unique = false, nullable = false)
    private String firstName;

    @Column(name = "lastName", unique = false, nullable = false)
    private String lastName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles roles;

    @JsonIgnore
    @ManyToMany
    @Column(name = "group_id")
    private Set<Group> groups;

    @JsonIgnore
    @OneToMany
    @Column(name = "post_id", unique = false, nullable = false)
    private Set<Post> posts;

    @JsonIgnore
    @OneToMany
    @Column(name = "reaction_id", unique = false, nullable = false)
    private Set<ReactionPost> reactions;

    @Column(name = "active")
    private String active;
}




