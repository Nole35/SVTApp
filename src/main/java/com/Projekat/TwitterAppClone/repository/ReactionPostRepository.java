package com.Projekat.TwitterAppClone.repository;

import com.Projekat.TwitterAppClone.model.ReactionPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionPostRepository extends JpaRepository<ReactionPost, Integer> {
}
