package com.skylord.firstProject.repository;

import com.skylord.firstProject.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<User, ObjectId> {
    User getUserByUserName(String userName);
}
