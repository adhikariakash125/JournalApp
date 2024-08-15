package com.skylord.firstProject.controller;

import com.skylord.firstProject.entity.User;
import com.skylord.firstProject.service.UserService;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public List<User> getAll(){
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable ObjectId id){
        return userService.getUserById(id);
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUserById(@RequestBody User user,@PathVariable @NonNull String username){
        User savedUser = userService.getUserByUserName(username);
        if (savedUser!=null){
            savedUser.setUserName(user.getUserName());
            savedUser.setPassword(user.getPassword());
            userService.saveUser(savedUser);
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
