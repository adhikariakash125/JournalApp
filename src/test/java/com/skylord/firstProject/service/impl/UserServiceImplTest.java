package com.skylord.firstProject.service.impl;

import com.skylord.firstProject.entity.User;
import com.skylord.firstProject.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepo userRepo;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUserByUserName() {
        when(userRepo.getUserByUserName(anyString())).thenReturn(Optional.of(new User("test","test")));
        assertEquals(userService.getUserByUserName(anyString()).getUserName(),"test");
    }
}