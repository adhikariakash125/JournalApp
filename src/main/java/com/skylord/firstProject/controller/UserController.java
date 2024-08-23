package com.skylord.firstProject.controller;

import com.skylord.firstProject.entity.User;
import com.skylord.firstProject.service.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<User> getUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUserName(userName);
        if (user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.updateByUserName(user, userName);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.deleteUserByUserName(userName);
    }

}
