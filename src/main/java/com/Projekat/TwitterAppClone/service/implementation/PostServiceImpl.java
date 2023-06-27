package com.Projekat.TwitterAppClone.service.implementation;

import com.Projekat.TwitterAppClone.dto.PostDTO;
import com.Projekat.TwitterAppClone.model.Post;
import com.Projekat.TwitterAppClone.repository.PostRepository;
import com.Projekat.TwitterAppClone.service.GroupService;
import com.Projekat.TwitterAppClone.service.PostService;
import com.Projekat.TwitterAppClone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;


    @Override
    public Optional<Post> findOne(Integer id) {
        return postRepository.findById(id);
    }

    @Override
    public Post getOne(Integer id) {
        return postRepository.getById(id);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post save(Post post) {
        try{
            return postRepository.save(post);
        }catch (IllegalArgumentException e){
            return null;
        }
    }

    @Override
    public Post createPost(PostDTO postDTO) {
        Optional<Post> post = postRepository.findById(postDTO.getPostId());

        if(post.isPresent()){

            return null;
        }


        Post newPost = new Post();
        newPost.setContent(postDTO.getContent());
        newPost.setCreationDate(LocalDate.now());
        newPost.setImagePath(postDTO.getImagePath());
        newPost.setGroup(groupService.getOneById(postDTO.getGroup()));
        newPost.setUser(userService.findOneById(postDTO.getUser()));
        newPost.setActive("true");

        newPost = postRepository.save(newPost);

        return newPost;
    }

    @Override
    public void delete(int id) {
        postRepository.deleteById(id);
    }
}
