package com.Projekat.TwitterAppClone.dto;

import com.Projekat.TwitterAppClone.model.Group;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class GroupDTO implements Serializable {
    private int groupId;
    private String name;
    private String description;
    private LocalDate creationDate;
    private boolean isSuspend;
    private int groupAdmin;

    public GroupDTO(Group createdGroup) {
        this.groupId = createdGroup.getGroupId();
        this.name = createdGroup.getName();
        this.description = createdGroup.getDescription();
        this.creationDate = createdGroup.getCreationDate();
        this.groupAdmin = createdGroup.getGroupAdmin().getUserId();
    }


}
