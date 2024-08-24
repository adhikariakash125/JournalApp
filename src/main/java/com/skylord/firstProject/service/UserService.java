package com.skylord.firstProject.service;

import com.skylord.firstProject.entity.User;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    List<User> getAllUsers();

    User getUserById(String userName);

    void deleteUserById(ObjectId id);

    User getUserByUserName(String userName);

    ResponseEntity<User> updateByUserName(User user, String userName);

    User createUser(User user);

    void deleteUserByUserName(String userName);

    User createAdmin(User user);
}
