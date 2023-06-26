package com.Projekat.TwitterAppClone.repository;

import com.Projekat.TwitterAppClone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findFirstByUsername(String username);

    Optional<User> findUserByUsernameAndPassword(String username, String password);

}
