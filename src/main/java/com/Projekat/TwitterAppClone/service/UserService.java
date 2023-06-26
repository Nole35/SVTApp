package com.Projekat.TwitterAppClone.service;

import com.Projekat.TwitterAppClone.dto.UserDTO;
import com.Projekat.TwitterAppClone.model.User;

import java.util.List;

public interface UserService {
    User findOne(String username);
    User findOneById(Integer id);
    List<User> findAll();
    User save(User user);

    User createUser(UserDTO userDTO);

    void delete(int id);
}
