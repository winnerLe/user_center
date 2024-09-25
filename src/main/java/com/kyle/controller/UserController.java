package com.kyle.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kyle.common.BaseResponse;
import com.kyle.common.ErrorCode;
import com.kyle.common.ResultUtils;
import com.kyle.exception.BusinessException;
import com.kyle.model.domain.User;
import com.kyle.model.domain.request.UserLoginRequest;
import com.kyle.model.domain.request.UserRegisterRequest;
import com.kyle.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.BaseStream;
import java.util.stream.Collectors;

import static com.kyle.constant.UserContast.ADMIN_ROLE;
import static com.kyle.constant.UserContast.USER_LOGIN_STATE;

@RequestMapping("/user")
@ResponseBody
@RestController
@Slf4j
public class UserController {
    @Resource
    private UserService userService;
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if(userRegisterRequest==null){
            throw new BusinessException(ErrorCode.NULL_ERROR,"请求参数为空");
        }

        String userAccount=userRegisterRequest.getUserAccount();
        String userPassword=userRegisterRequest.getUserPassword();
        String checkPassword=userRegisterRequest.getCheckPassword();
        String planteCode=userRegisterRequest.getPlanteCode();
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,planteCode)){
            throw new BusinessException(ErrorCode.NULL_ERROR,"请求参数为空");
        }
        long result= userService.userRegister(userAccount, userPassword, checkPassword, planteCode);
        return ResultUtils.success(result);
    }
    @GetMapping("/current")
    public BaseResponse<User>  getCurrentUser (HttpServletRequest request){
        Object userobj=request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser =(User)userobj;
        if(currentUser==null){
            return null;
        }
        long userID=currentUser.getId();
        User user=userService.getById(userID);
        User user1=userService.getSafetyUser(user);
        return ResultUtils.success(user1);
    }
    @PostMapping("/login")
    @ResponseBody
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if(userLoginRequest==null){
            return null;
        }

        String userAccount=userLoginRequest.getUseraccount();
        String userPassword=userLoginRequest.getUserpassword();
        log.info(userAccount);
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        User user= userService.doLogin(userAccount, userPassword,request);
        return ResultUtils.success(user);
    }


    /**
     * 用户注销
     */


    @PostMapping("/logout")

    public BaseResponse<Integer> userLogin(  HttpServletRequest request){
        if(request==null){
            return null;
        }

       Integer integer= userService.UserLogOUt(request);
        return ResultUtils.success(integer);
    }

    /**
     *
     * @param username
     * @param request
     * @return
     */
    @GetMapping("/search")
    public BaseResponse<List<User>>searchUsers(String username,HttpServletRequest request){
        Object userobject= request.getSession().getAttribute(USER_LOGIN_STATE);
        User user=(User)userobject;
        if(user==null|| user.getUserRole()!=ADMIN_ROLE){
            List<User> users= new ArrayList<>();
            return ResultUtils.success(users);
        }

        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)){
            queryWrapper.like("username",username);
        }
        List<User> userlist = userService.list(queryWrapper);
        List<User> userList= userlist.stream().map(user1 ->userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(userList);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser (@RequestBody long id ,HttpServletRequest request){
        Object userobject= request.getSession().getAttribute(USER_LOGIN_STATE);
        User user=(User)userobject;
        if(user==null|| user.getUserRole()!=ADMIN_ROLE){
            return null;
        }
        if(id<=0){
            return null;
        }
        Boolean b=  userService.removeById(id);
        return ResultUtils.success(b);

    }


}
