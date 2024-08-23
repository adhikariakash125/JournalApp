package com.skylord.firstProject.service.impl;

import com.skylord.firstProject.entity.User;
import com.skylord.firstProject.repository.UserRepo;
import com.skylord.firstProject.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepo userRepo;

    @Override
    public User saveUser(User user){
        return userRepo.save(user);
    }

    @Override
    public User createUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        return userRepo.save(user);
    }

    @Override
    public ResponseEntity<?> deleteUserByUserName(String userName) {
        User user = getUserByUserName(userName);
        userRepo.delete(user);
        return new ResponseEntity<>(HttpStatus.OK);
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
        User savedUser = getUserByUserName(user.getUserName());
        if (savedUser!=null){
            savedUser.setUserName(user.getUserName());
            savedUser.setPassword(user.getPassword());
            createUser(savedUser);
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
