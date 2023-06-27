package com.Projekat.TwitterAppClone.service.implementation;

import com.Projekat.TwitterAppClone.dto.GroupDTO;
import com.Projekat.TwitterAppClone.model.Group;
import com.Projekat.TwitterAppClone.repository.GroupRepository;
import com.Projekat.TwitterAppClone.service.GroupService;
import com.Projekat.TwitterAppClone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserService userService;

    @Override
    public Optional<Group> findOne(int id) {
        return groupRepository.findById(id);
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public Group save(Group group) {
        try{
            return groupRepository.save(group);
        }catch (IllegalArgumentException e){
            return null;
        }
    }

    @Override
    public Group getOneById(int id) {
        return groupRepository.getById(id);
    }

    @Override
    public Group findOneByName(String groupName) {
        Group group = groupRepository.findGroupByName(groupName);
        if (group != null) {
            return group;
        }
        return null;
    }


    @Override
    public Group createGroup(GroupDTO groupDTO) {
        Optional<Group> group = groupRepository.findById(groupDTO.getGroupId());

        if(group.isPresent()){
            return null;
        }

        Group newGroup = new Group();
        newGroup.setName(groupDTO.getName());
        newGroup.setDescription(groupDTO.getDescription());
        newGroup.setCreationDate(LocalDate.now());
        newGroup.setGroupAdmin(userService.findOneById(groupDTO.getGroupAdmin()));
        newGroup.setActive("true");

        newGroup = groupRepository.save(newGroup);

        return newGroup;
    }

    @Override
    public void delete(int id) {
        groupRepository.deleteById(id);
    }
}
