package com.Projekat.TwitterAppClone.service;

import com.Projekat.TwitterAppClone.dto.GroupDTO;
import com.Projekat.TwitterAppClone.model.Group;

import java.util.List;
import java.util.Optional;

public interface GroupService {

    Optional<Group> findOne(int id);
    List<Group> findAll();
    Group save(Group group);

    Group getOneById(int id);

    Group findOneByName(String groupName);

    Group createGroup(GroupDTO groupDTO);

    void delete(int id);
}
