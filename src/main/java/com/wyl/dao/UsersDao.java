package com.wyl.dao;

import com.wyl.pojo.Users;
import org.apache.ibatis.annotations.Mapper;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

//注意每个dao层都必须有mapper这个注解，这样dao层才可以使用
@Mapper
//登录需要账号和密码，写一下接口
public interface UsersDao {
    //学生登录
    Users login(String username,String userpwd);
    //添加学生到数据库
    Integer addUsers(Users users);
    Users getByName(String username);
    List<Users> pageById(Integer classid);
    Integer updateUsers(Users users);
    Users getByUserid(Integer userid);
    Integer delUserid(Integer userid);
    //通过班级id删除
    Integer delClassid(Integer classid);
    //修改密码
    Integer upUserPw(String userpwd,Integer userid);
    //用户名查询id
    Integer findUserIdByName(String username);
    //用户名查询真实姓名
    String findTrueName(String username);
    //查询班级id通过用户名
    Integer findClassIdByName(String username);
    //查询所有学生id
    List<Users> getUserid(Integer classid);
}
