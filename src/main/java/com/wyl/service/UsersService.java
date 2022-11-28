package com.wyl.service;

import com.wyl.pojo.Users;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

// 服务层接口
public interface UsersService {
    Users login(String username, String userpwd);
    //写一下接口和接口的实现方法
    Integer addUsers(Users users);
    Users getByName(String username);
    List<Users> pageByClassid(Integer classid);
    Integer updateUsers(Users users);
    Users getByUserid(Integer userid);
    Integer delUserid(Integer userid);
    Integer delClassid(Integer classid);
    Integer updateUserPwd(String userpwd,Integer userid);
    Integer findUserIdByName(String username);
    String findTrueNameByName(String username);
    //通过name获取班级id
    Integer findClssIdByName(String username);

}
