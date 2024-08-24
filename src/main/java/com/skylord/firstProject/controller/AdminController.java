package com.skylord.firstProject.controller;

import com.skylord.firstProject.entity.User;
import com.skylord.firstProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAll(){
        List<User> allUsers = userService.getAllUsers();
        if (allUsers!=null && !allUsers.isEmpty()) {
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/create-new-admin")
    public ResponseEntity<User> createNewAdmin(@RequestBody User user) {
        User savedAdmin = userService.createAdmin(user);
        return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED);
    }
}
