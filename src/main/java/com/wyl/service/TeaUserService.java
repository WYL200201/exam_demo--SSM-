package com.wyl.service;

import com.wyl.pojo.TeaUser;

public interface TeaUserService {
    //实现登录接口和实现方法
    TeaUser teaLogin(String username,String userpwd);
    String getTeaTruename(String username);
    Integer getClassId(String username);
    Integer getTeaIdByName(String username);
}
