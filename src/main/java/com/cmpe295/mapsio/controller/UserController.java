package com.cmpe295.mapsio.controller;

import com.cmpe295.mapsio.service.GoogleService;
import com.cmpe295.mapsio.service.UserService;
import com.cmpe295.mapsio.domain.User;
import com.cmpe295.mapsio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author arunabh.shrivastava
 */

@RestController
public class UserController {

    private final
    UserRepository userRepository;
    private final
    UserService userService;
    private final
    GoogleService googleService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService, GoogleService googleService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.googleService = googleService;
    }

    @PostMapping(value = "/api/users/authCode")
    public ResponseEntity<?> setUserAuthCode(@RequestBody User user) {
        user = googleService.getUserAccessToken(user);
        user = userService.getUserCalendarEvents(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/api/users/test")
    public ResponseEntity<?> test(@RequestBody User user){
        Optional<User> user1 = userRepository.findById(user.getId());
        user1.ifPresent(userService::getUserCalendarEvents);
        return new ResponseEntity<>(user1, HttpStatus.OK);
    }
}