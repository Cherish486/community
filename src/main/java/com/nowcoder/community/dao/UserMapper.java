package com.nowcoder.community.dao;

import com.nowcoder.community.entity.User;
import org.apache.ibatis.annotations.Mapper;

// @Mapper功能和@Repository 一样，只是@Mapper更简短
@Mapper
public interface UserMapper {

    User selectById(int id);

    User selectByName(String username);

    User selectByEmail(String email);

    int insertUser(User user);

    int updateStatus(int id , int status);

    int updateHeader(int id , String headerUrl);

    int updatePassword(int id , String password);

}
