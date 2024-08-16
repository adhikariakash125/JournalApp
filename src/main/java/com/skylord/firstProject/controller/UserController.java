package com.skylord.firstProject.controller;

import com.skylord.firstProject.entity.User;
import com.skylord.firstProject.service.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        List<User> allUsers = userService.getAllUsers();
        if (allUsers!=null && !allUsers.isEmpty()) {
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<User> getUserByUserName(@PathVariable String userName){
        User user = userService.getUserById(userName);
        if (user!=null)
            return new ResponseEntity<>(user,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUserById(@RequestBody User user,@PathVariable @NonNull String username){
        return userService.updateByUserName(user,username);
    }

}
