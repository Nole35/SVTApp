package com.Projekat.TwitterAppClone.service;

import com.Projekat.TwitterAppClone.dto.PostDTO;
import com.Projekat.TwitterAppClone.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    Optional<Post> findOne(Integer id);
    Post getOne(Integer id);
    List<Post> findAll();
    Post save(Post post);
    Post createPost(PostDTO postDTO);
    void delete(int id);
}
