package com.Projekat.TwitterAppClone.controller;

import com.Projekat.TwitterAppClone.dto.KarmaDTO;
import com.Projekat.TwitterAppClone.dto.ReactionPostDTO;
import com.Projekat.TwitterAppClone.enums.ReactionType;
import com.Projekat.TwitterAppClone.model.Post;
import com.Projekat.TwitterAppClone.model.ReactionPost;
import com.Projekat.TwitterAppClone.service.PostService;
import com.Projekat.TwitterAppClone.service.ReactionPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "api/reactionPost")
public class ReactionPostController {

    @Autowired
    private ReactionPostService reactionPostService;

    @Autowired
    private PostService postService;

    Logger logger = Logger.getLogger(ReactionPostController.class.getName());

    @GetMapping
    public ResponseEntity<List<ReactionPostDTO>> findAll(){

        List<ReactionPost> reactionPosts = reactionPostService.findAll();
        List<ReactionPostDTO> reactionPostDTOS = new ArrayList<>();
        for (ReactionPost reactionPost : reactionPosts){
            reactionPostDTOS.add(new ReactionPostDTO(reactionPost));
        }

        return new ResponseEntity<>(reactionPostDTOS, HttpStatus.OK);
    };

    @GetMapping("/post/{postId}")
    public ResponseEntity<KarmaDTO> getPostKarma(@PathVariable("postId") Integer id){
        KarmaDTO karmaDTO = new KarmaDTO();
        karmaDTO.setKarma(0);
        int like = 0;
        int dislike = 0;
        int heart = 0;

        Post onePost = postService.getOne(id);
        List<ReactionPost> reactionPostList = reactionPostService.findAll();
        List<ReactionPost> onePostReaction = new ArrayList<>();



        for (ReactionPost reactionPost : reactionPostList){
            if (reactionPost.getPost().getPostId() == id){
                onePostReaction.add(reactionPost);
            }
        }

        for(ReactionPost reactionPost : onePostReaction){
            if (reactionPost.getType() == ReactionType.LIKE){
                like = like + 1;
            }else if(reactionPost.getType() == ReactionType.DISLIKE){
                dislike = dislike + 1;
            }else if(reactionPost.getType() == ReactionType.HEART){
                heart = heart + 1;
            }
        }

        karmaDTO.setKarma(like - dislike - heart);


        return new ResponseEntity<>(karmaDTO, HttpStatus.OK);
    }

    @GetMapping("/postByUser/{username}")
    public ResponseEntity<KarmaDTO> getPostKarmaByOneUser(@PathVariable("username") String username){
        KarmaDTO karmaDTO = new KarmaDTO();
        karmaDTO.setKarma(0);
        int like = 0;
        int dislike = 0;

        List<ReactionPost> reactionPostList = reactionPostService.findAll();
        List<ReactionPost> onePostReaction = new ArrayList<>();

        for (ReactionPost reactionPost : reactionPostList){
            if (reactionPost.getPost().getUser().getUsername().equals(username)){
                onePostReaction.add(reactionPost);
            }
        }

        for(ReactionPost reactionPost : onePostReaction){
            if (reactionPost.getType() == ReactionType.LIKE){
                like = like + 1;
            }else{
                dislike = dislike + 1;
            }
        }

        karmaDTO.setKarma(like - dislike);

        return new ResponseEntity<>(karmaDTO, HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<ReactionPostDTO> create(@RequestBody ReactionPostDTO newReaction){

        boolean isOk = true;

        List<ReactionPost> reactionPosts = reactionPostService.findAll();

        for (ReactionPost reactionPost: reactionPosts){
            if (reactionPost.getUser().getUserId() == newReaction.getUserId() && reactionPost.getType() == ReactionType.LIKE
                    && newReaction.getReactionType() == ReactionType.LIKE && reactionPost.getPost().getPostId() == newReaction.getPostId()){

                isOk =  false;

            }else if(reactionPost.getUser().getUserId() == newReaction.getUserId() && reactionPost.getType() == ReactionType.LIKE
                    && newReaction.getReactionType() == ReactionType.DISLIKE && reactionPost.getPost().getPostId() == newReaction.getPostId()){
                reactionPostService.delete(reactionPost.getReactionId());
            }
        }


        for (ReactionPost reactionPost: reactionPosts){
            if (reactionPost.getUser().getUserId() == newReaction.getUserId() && reactionPost.getType() == ReactionType.DISLIKE
                    && newReaction.getReactionType() == ReactionType.DISLIKE && reactionPost.getPost().getPostId() == newReaction.getPostId()){

                isOk = false;


            }else if(reactionPost.getUser().getUserId() == newReaction.getUserId() && reactionPost.getType() == ReactionType.DISLIKE
                    && newReaction.getReactionType() == ReactionType.LIKE && reactionPost.getPost().getPostId() == newReaction.getPostId()){

                reactionPostService.delete(reactionPost.getReactionId());
            }

        }

        if (isOk){
            ReactionPost createdReaction = reactionPostService.createReaction(newReaction);
            ReactionPostDTO reactionPostDTO = new ReactionPostDTO(createdReaction);
            return new ResponseEntity<>(reactionPostDTO, HttpStatus.CREATED);
        }


        return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
    }


}
