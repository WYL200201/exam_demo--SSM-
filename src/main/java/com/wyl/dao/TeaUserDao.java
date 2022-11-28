package com.wyl.dao;

import com.wyl.pojo.TeaUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeaUserDao {
    TeaUser teaLogin(String username,String userpwd);
    String getTeaTruename(String username);
    //获取教师班级id
    Integer getClassId(String username);
    //通过教师用户名来查询id
    Integer getTeaIdByName(String username);
}
