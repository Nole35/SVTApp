package com.Projekat.TwitterAppClone.repository;

import com.Projekat.TwitterAppClone.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

    //Optional<Group> findGroupByName(String groupName);

    Group findGroupByName(String groupName);

}
