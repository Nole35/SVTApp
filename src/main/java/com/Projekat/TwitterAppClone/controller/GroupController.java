package com.Projekat.TwitterAppClone.controller;

import com.Projekat.TwitterAppClone.dto.GroupDTO;
import com.Projekat.TwitterAppClone.enums.Roles;
import com.Projekat.TwitterAppClone.model.Group;
import com.Projekat.TwitterAppClone.model.Post;
import com.Projekat.TwitterAppClone.model.User;
import com.Projekat.TwitterAppClone.service.GroupService;
import com.Projekat.TwitterAppClone.service.PostService;
import com.Projekat.TwitterAppClone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "api/`group`")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    Logger logger = Logger.getLogger(GroupController.class.getName());

    @GetMapping
    public ResponseEntity<List<GroupDTO>> findAll(){

        List<Group> groups = groupService.findAll();
        List<GroupDTO> groupDTOS = new ArrayList<>();
        for (Group group : groups){
            if (group.getActive().equals("true")){
                groupDTOS.add(new GroupDTO(group));
            }

        }

        return new ResponseEntity<>(groupDTOS, HttpStatus.OK);
    };

    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getOne(@PathVariable("id") Integer id){
        Group group = groupService.getOneById(id);

        if (group.getActive().equals("false")){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        GroupDTO groupDTO = new GroupDTO(group);

        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }

    @GetMapping("/byGroupName/{groupName}")
    public ResponseEntity<GroupDTO> getOneByGroupName(@PathVariable("groupName") String groupName){

        Group group = groupService.findOneByName(groupName);
        if(group == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        GroupDTO groupDTO = new GroupDTO(group);

        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<GroupDTO> create(@RequestBody GroupDTO newGroup){

        Group createdGroup = groupService.createGroup(newGroup);

        if(createdGroup == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        GroupDTO groupDTO = new GroupDTO(createdGroup);

        logger.info("THE GROUP HAS BEEN SUCCESSFULLY CREATED!");

        return new ResponseEntity<>(groupDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<GroupDTO> updateGroup(@RequestBody GroupDTO groupDTO, @PathVariable("id") Integer id) {
        Group group = groupService.getOneById(id);

        if (group == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        group.setName(groupDTO.getName());
        group.setDescription(groupDTO.getDescription());
        group.setCreationDate(groupDTO.getCreationDate());
        group.setGroupAdmin(userService.findOneById(group.getGroupAdmin().getUserId()));

        group = groupService.save(group);

        return new ResponseEntity<>(new GroupDTO(group), HttpStatus.OK);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<GroupDTO> deleteGroup(@PathVariable Integer id) {

        List<Post> allPosts = postService.findAll();
        List<Post> groupPosts = new ArrayList<>();
        List<Group> allGroup = groupService.findAll();
        Group group = groupService.getOneById(id);

        int countGroupGroupAdmin = 0;

        if (group == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        for(Group group1:allGroup){
            if (group1.getGroupAdmin().getUserId() == group.getGroupAdmin().getUserId() && group1.getActive().equals("true")){
                countGroupGroupAdmin = countGroupGroupAdmin + 1;
            }
        }

        if (countGroupGroupAdmin == 1){
            User groupGroupAdmin = userService.findOneById(group.getGroupAdmin().getUserId());
            groupGroupAdmin.setRoles(Roles.USER);
            userService.save(groupGroupAdmin);
        }


        group.setActive("false");

        group = groupService.save(group);

        for (Post post: allPosts){
            if(post.getGroup().getGroupId() == id){
                groupPosts.add(post);
            }
        }

        for (Post post: groupPosts ){
            post.setActive("false");
            postService.save(post);
        }



        logger.info("THE GROUP HAS BEEN SUCCESSFULLY DELETED!");

        return new ResponseEntity<>(new GroupDTO(group), HttpStatus.OK);
    }

}
