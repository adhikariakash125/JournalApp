package com.skylord.firstProject.service;

import com.skylord.firstProject.entity.User;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);

    List<User> getAllUsers();

    User getUserById(ObjectId id);

    void deleteUserById(ObjectId id);

    User getUserByUserName(String userName);
}
