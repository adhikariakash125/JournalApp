package com.skylord.firstProject.service.impl;

import com.skylord.firstProject.entity.User;
import com.skylord.firstProject.repository.UserRepo;
import com.skylord.firstProject.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    public User saveUser(User user){
        return userRepo.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(String userName) {
        return userRepo.getUserByUserName(userName).orElse(null);
    }

    @Override
    public void deleteUserById(ObjectId id) {
        userRepo.deleteById(id);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepo.getUserByUserName(userName).orElse(null);
    }

    @Override
    public ResponseEntity<User> updateByUserName(User user, String userName) {
        User savedUser = getUserByUserName(userName);
        if (savedUser!=null){
            savedUser.setUserName(user.getUserName());
            savedUser.setPassword(user.getPassword());
            saveUser(savedUser);
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
