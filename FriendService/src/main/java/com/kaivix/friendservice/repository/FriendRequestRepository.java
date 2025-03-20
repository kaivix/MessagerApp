package com.kaivix.friendservice.repository;

import com.kaivix.friendservice.model.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    List<FriendRequest> findFriendRequestByRequestFriendId(Long requestFriendId);

    boolean existsByRequesterIdAndRequestFriendId(Long requesterId, Long requesterFriendId);
}
