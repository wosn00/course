package com.hs.course.dao;

import com.hs.course.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface UserMapper {
     void regSaveUser(User user);
     User findByName(String name);
     int findRoleByName(String name);
     User findByPhone(String phone);
     void updateByPhone(User user);
     List<User> selFenye(Map map);

}
