package com.kyle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyle.common.ErrorCode;
import com.kyle.exception.BusinessException;
import com.kyle.mapper.UserMapper;
import com.kyle.model.domain.User;
import com.kyle.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.kyle.constant.UserContast.USER_LOGIN_STATE;

/**
* @author 郝佳锐
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-09-07 16:10:10
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{


    private static final String SALT="winner";
    private final UserMapper userMapper;
    /**
     * 用户登录态键
     */

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword,String planteCode) {
        //三者是否有空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword,planteCode)) {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR,"用户参数为空");

        }
        //星球编码长度不能超过5
        if(planteCode.length()>5){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR,"星球编号过长");
        }
//账户长度是否符合要求
        if(userAccount.length()<4){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR,"用户名过短");
        }
//密码长度
        if(userPassword.length()<8||checkPassword.length()<8){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR,"密码长度过短");
        }
        //判断账号输入是否合法
        String vaildPattern="\\pP|\\pS|\\s+";
        Matcher matcher= Pattern.compile(vaildPattern).matcher(userAccount);
        if(matcher.find()){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR,"账号不合法");
        }

        //判断两次密码是否相同
        if(!userPassword.equals(checkPassword)){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR,"两次密码相同");
        }


        //查找是否有用户名相同用户
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("useraccount",userAccount);
        long count=this.count(queryWrapper);
        if(count>0){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR,"有同名用户");
        }
//判断星球用户编号是否唯一
        queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("planteCode",planteCode);
        count=this.count(queryWrapper);
        if(count>0){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR,"星球编号不唯一");
        }

        String encryptPassword= DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());
        User user=new User();
        user.setUseraccount(userAccount);
        user.setUserpassword(encryptPassword);
        user.setPlanteCode(planteCode);
        boolean result= this.save(user);
        if(!result){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR,"保存失败");
        }



        return user.getId();
    }


    @Override
    public User doLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //账号密码是否为空

        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }

        if(userAccount.length()<4){
            return null;
        }

        if(userPassword.length()<8){
            return null;
        }

        String vaildPattern="\\pP|\\pS|\\s+";
        Matcher matcher= Pattern.compile(vaildPattern).matcher(userAccount);

        if(matcher.find()){
            return null;
        }

        String encryptPassword= DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());

        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("useraccount",userAccount);
        queryWrapper.eq("userpassword",encryptPassword);
        List<User> list = userMapper.selectList(queryWrapper);
        log.info("login failed");
        if(list==null||list.isEmpty()){
            log.info("login failed");
            return null;
        }

        User user = list.get(0);


        User safetyUser=getSafetyUser(user);

        request.getSession().setAttribute(USER_LOGIN_STATE,safetyUser);
        return safetyUser;
    }
@Override
    public User getSafetyUser(User originuser){
        if(originuser==null){
            return null;
        }
        User safetyUser =new User();
        safetyUser.setId(originuser.getId());
        safetyUser.setUsername(originuser.getUsername());
        safetyUser.setUseraccount(originuser.getUseraccount());
        safetyUser.setAvataUrl(originuser.getAvataUrl());
        safetyUser.setGender(originuser.getGender());
        safetyUser.setUserpassword(originuser.getUserpassword());
        safetyUser.setPhone(originuser.getPhone());
        safetyUser.setPlanteCode(originuser.getPlanteCode());
        safetyUser.setUserRole(originuser.getUserRole());
        safetyUser.setEmail(originuser.getEmail());
        safetyUser.setUserstatus(originuser.getUserstatus());
        safetyUser.setCreateTime(originuser.getCreateTime());
        safetyUser.setUpdateTime(originuser.getUpdateTime());
        safetyUser.setIsDelete(originuser.getIsDelete());
        return safetyUser;
    }

    @Override
    public int UserLogOUt(HttpServletRequest request) {
     request.getSession().removeAttribute(USER_LOGIN_STATE);
     return 1;
    }
}





