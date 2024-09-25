package com.kyle.service;

import com.kyle.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;


/**
* @author 郝佳锐
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-09-07 16:10:10
*/
public interface UserService extends IService<User> {
    /**
     *
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return
     */
    long userRegister(String userAccount , String userPassword ,String checkPassword,String planteCode);

    /**
     * @param userAccount
     * @param userPassword
     * @param request
     * @return
     */
    User doLogin(String userAccount, String  userPassword, HttpServletRequest request);


    User getSafetyUser(User originuser);

    /**
     * 用户注销
     * @param request
     * @return
     */
    int UserLogOUt(HttpServletRequest request);
}

