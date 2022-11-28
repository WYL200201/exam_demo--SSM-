package com.wyl.service.impl;

import com.wyl.dao.TeaUserDao;
import com.wyl.pojo.TeaUser;
import com.wyl.service.TeaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeaUserServiceImpl implements TeaUserService {
    @Autowired
    TeaUserDao teaUserDao;
    @Override
    public TeaUser teaLogin(String username, String userpwd) {
        return teaUserDao.teaLogin(username,userpwd);
    }

    @Override
    public String getTeaTruename(String username) {
        return teaUserDao.getTeaTruename(username);
    }

    @Override
    public Integer getClassId(String username) {
        return teaUserDao.getClassId(username);
    }

    @Override
    public Integer getTeaIdByName(String username) {
        return teaUserDao.getTeaIdByName(username);
    }
}
