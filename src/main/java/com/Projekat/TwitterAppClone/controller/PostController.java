package com.Projekat.TwitterAppClone.controller;

import com.Projekat.TwitterAppClone.dto.PostDTO;
import com.Projekat.TwitterAppClone.dto.ReactionPostDTO;
import com.Projekat.TwitterAppClone.enums.ReactionType;
import com.Projekat.TwitterAppClone.model.Post;
import com.Projekat.TwitterAppClone.model.ReactionPost;
import com.Projekat.TwitterAppClone.service.GroupService;
import com.Projekat.TwitterAppClone.service.PostService;
import com.Projekat.TwitterAppClone.service.ReactionPostService;
import com.Projekat.TwitterAppClone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ReactionPostService reactionPostService;

    Logger logger = Logger.getLogger(PostController.class.getName());

    @GetMapping
    public ResponseEntity<List<PostDTO>> findAll(){

        List<Post> posts = postService.findAll();
        List<PostDTO> postDTOS = new ArrayList<>();
        for (Post post : posts){
            if (post.getActive().equals("true")){
                postDTOS.add(new PostDTO(post));
            }

        }

        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
    };


    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<PostDTO>> getPostsByGroup(@PathVariable("groupId") Integer id){

        List<Post> posts = postService.findAll();
        List<PostDTO> postDTOS = new ArrayList<>();
        for (Post post : posts){
            if(post.getGroup().getGroupId() == id && post.getActive().equals("true")){
                postDTOS.add(new PostDTO(post));

            }

        }
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
    };

    @GetMapping("/user/{username}")
    public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable("username") String username){

        List<Post> posts = postService.findAll();

        List<PostDTO> postDTOS = new ArrayList<>();
        for (Post post : posts){
            if(post.getUser().getUsername().equals(username) && post.getActive().equals("true")){
                postDTOS.add(new PostDTO(post));

            }

        }
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
    };


    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getOne(@PathVariable("id") Integer id){
        Post posts = postService.getOne(id);

        if (posts.getActive().equals("false")){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        PostDTO postDTO = new PostDTO(posts);

        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<PostDTO> create(@RequestBody PostDTO newPost){

        Post createdPost = postService.createPost(newPost);


        if(createdPost == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        PostDTO postDTO = new PostDTO(createdPost);

        //CREATE REACTION FOR POST
        ReactionPostDTO reactionPost = new ReactionPostDTO();
        reactionPost.setPostId(postDTO.getPostId());
        reactionPost.setReactionType(ReactionType.LIKE);
        reactionPost.setUserId(postDTO.getUser());
        ReactionPost createdReaction = reactionPostService.createReaction(reactionPost);

        logger.info("USER HAS CREATED A NEW POST");

        return new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<PostDTO> deletePost(@PathVariable Integer id) {

        Post post = postService.getOne(id);

        if (post == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        post.setActive("false");

        post = postService.save(post);

        logger.info("USER HAS DELETE HIS POST");

        return new ResponseEntity<>(new PostDTO(post), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable("id") Integer id) {
        Post post = postService.getOne(id);

        if (post == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        post.setContent(postDTO.getContent());
        post.setGroup(groupService.getOneById(post.getGroup().getGroupId()));
        post.setUser(userService.findOneById(post.getUser().getUserId()));

        post = postService.save(post);

        logger.info("USER EDITED HIS POST");

        return new ResponseEntity<>(new PostDTO(post), HttpStatus.OK);
    }

}
