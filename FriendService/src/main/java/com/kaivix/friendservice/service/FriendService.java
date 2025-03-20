package com.kaivix.friendservice.service;

import com.kaivix.friendservice.model.FriendRequest;
import com.kaivix.friendservice.model.Friends;
import com.kaivix.friendservice.repository.FriendRepository;
import com.kaivix.friendservice.repository.FriendRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final FriendRequestRepository friendRequestRepository;


    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<String> createFriendRequest(Long requesterId, Long requestFriendId) {
        if (friendRequestRepository.existsByRequesterIdAndRequestFriendId(requesterId, requestFriendId)) {
            log.warn("Friend request already exists: {} → {}", requesterId, requestFriendId);
            return ResponseEntity.badRequest().body("Запрос в друзья уже существует");
        }

        FriendRequest request = FriendRequest.builder()
                .requesterId(requesterId)
                .requestFriendId(requestFriendId)
                .build();

        friendRequestRepository.save(request);
        log.info("Friend request sent: {} → {}", requesterId, requestFriendId);

        return ResponseEntity.ok("Запрос в друзья отправлен");
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public void createFriendLink(Long requesterId, Long requestFriendId) {
        Friends friendLink = Friends.builder()
                .friendId(requestFriendId)
                .userId(requesterId)
                .build();
        friendRepository.save(friendLink);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public void getFriendRequests(Long userId){
        friendRequestRepository.findFriendRequestByRequestFriendId(userId);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void deleteFriendRequest(Long requestId) {
        friendRequestRepository.deleteById(requestId);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void deleteFriendLink(Long linkId) {
        friendRepository.deleteById(linkId);
    }

}
