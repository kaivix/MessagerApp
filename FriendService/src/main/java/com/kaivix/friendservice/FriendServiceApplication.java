package com.kaivix.friendservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableFeignClients
@SpringBootApplication
public class FriendServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FriendServiceApplication.class, args);
    }

}
