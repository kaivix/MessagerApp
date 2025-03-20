package com.kaivix.friendservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "requests")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;
    private Long requestFriendId;
    private Long requesterId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequestFriendId() {
        return requestFriendId;
    }

    public void setRequestFriendId(Long requestFriendId) {
        this.requestFriendId = requestFriendId;
    }

    public Long getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(Long requesterId) {
        this.requesterId = requesterId;
    }
}
