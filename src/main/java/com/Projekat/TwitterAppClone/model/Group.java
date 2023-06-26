package com.Projekat.TwitterAppClone.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name="`group`")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", nullable = false)
    private int groupId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "creationDate")
    private LocalDate creationDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User groupAdmin;

    @Column(name = "active")
    private String active;
}
