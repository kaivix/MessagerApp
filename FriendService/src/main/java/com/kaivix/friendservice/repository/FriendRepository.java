package com.kaivix.friendservice.repository;

import com.kaivix.friendservice.model.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friends, Long> {

}
