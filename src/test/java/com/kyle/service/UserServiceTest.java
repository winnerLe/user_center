package com.kyle.service;
import java.util.Date;

import com.kyle.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClient;

//import javax.annotation.Resource;


/*
* 用户注释
*@author winner
*
* */
@SpringBootTest

class UserServiceTest {

  private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);
  @Autowired
  private UserService userService;

//@Test
//  void testAdduser() {
//    User user=new User();
//
//    user.setUsername("winner");
//    user.setUseraccount("123");
//    user.setAvataUrl("asdd");
//
//    user.setUserpassword("11111");
//    user.setPhone("111");
//    user.setEmail("1");
//
//
//  boolean result = userService.save(user);
//  Assertions.assertTrue(result);
//}
//
//  @Test
//  void userRegister() {
//
//  String acc="winner2";
//  String pa="123456";
//  String ch="123456";
//
//
//
//  }
}