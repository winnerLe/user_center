package com.kyle.mapper;

import com.kyle.model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
* @author 郝佳锐
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2024-09-07 16:10:10
* @Entity generator.domain.User
*/
@Mapper

public interface UserMapper extends BaseMapper<User> {

}




