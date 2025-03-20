package com.kaivix.friendservice.controller;

import com.kaivix.friendservice.service.FriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class FriendController {

    private final FriendService friendService;

    @Autowired
    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @RequestMapping(value = "/friend/addrequest", method = RequestMethod.POST)
    public ResponseEntity<String> addFriendRequest(@RequestParam Long requesterId, @RequestParam Long requestFriendId) {

        log.info("Received friend request: {} → {}", requesterId, requestFriendId);
        return friendService.createFriendRequest(requesterId, requestFriendId);
    }

    @RequestMapping(value = "/friend/reponse", method = RequestMethod.POST)
    public ResponseEntity<String> requestResponce(@RequestParam Long requesterId,
                                @RequestParam Long requestFriendId,
                                @RequestParam Long requestId ,
                                @RequestParam Boolean respond) {
        if (respond) {
            try {
                friendService.createFriendLink(requesterId, requestFriendId);
                friendService.deleteFriendRequest(requestId);
                log.info("Friend request accepted");
                return ResponseEntity.ok("Friend request accepted");
            }
            catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }

        }else {
            friendService.deleteFriendRequest(requestId);
            log.info("Friend request rejected");
            return ResponseEntity.ok("Friend request rejected");
        }

    }

    @RequestMapping(value = "/friend/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteFriendRequest(@PathVariable Long id) {
        try {
            friendService.deleteFriendLink(id);
            return ResponseEntity.ok("Friend request deleted");
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
