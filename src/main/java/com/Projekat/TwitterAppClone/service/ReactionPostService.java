package com.Projekat.TwitterAppClone.service;

import com.Projekat.TwitterAppClone.dto.ReactionPostDTO;
import com.Projekat.TwitterAppClone.model.ReactionPost;

import java.util.List;
import java.util.Optional;

public interface ReactionPostService {

    Optional<ReactionPost> findOne(int id);
    ReactionPost getOne(int id);
    ReactionPost createReaction(ReactionPostDTO reactionDTO);
    List<ReactionPost> findAll();
    ReactionPost save(ReactionPost reaction);
    void delete(int id);
}
