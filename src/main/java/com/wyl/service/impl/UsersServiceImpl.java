package com.wyl.service.impl;

import com.wyl.dao.StuScoreDao;
import com.wyl.dao.UsersDao;
import com.wyl.pojo.Users;
import com.wyl.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//实现一下service层接口
public class UsersServiceImpl implements UsersService {
    //将UsersDao自动注入
    @Autowired
    UsersDao usersDao;
    @Autowired
    StuScoreDao stuScoreDao;
    @Override
    public Users login(String username, String userpwd) {
        Users login=usersDao.login(username,userpwd);
        return login;
    }

    @Override
    public Integer addUsers(Users users) {
        return usersDao.addUsers(users);
    }

    @Override
    public Users getByName(String username) {
        return usersDao.getByName(username);
        //返回学生controller层
    }

    @Override
    public List<Users> pageByClassid(Integer classid) {
        return usersDao.pageById(classid);
    }

    @Override
    public Integer updateUsers(Users users) {
        return usersDao.updateUsers(users);
    }

    @Override
    public Users getByUserid(Integer userid) {
        return usersDao.getByUserid(userid);
    }

    @Override
    public Integer delUserid(Integer userid) {
        stuScoreDao.delateByUser(userid);
        return usersDao.delUserid(userid);
    }

    @Override
    public Integer delClassid(Integer classid) {
        List<Users> userid = usersDao.getUserid(classid);
        for (Users users : userid) {
            Integer userid1 = users.getUserid();
            stuScoreDao.delateByUser(userid1);
        }
        return usersDao.delClassid(classid);
    }

    @Override
    public Integer updateUserPwd(String userpwd, Integer userid) {
        return usersDao.upUserPw(userpwd,userid);
    }

    @Override
    public Integer findUserIdByName(String username) {
        return usersDao.findUserIdByName(username);
    }

    @Override
    public String findTrueNameByName(String username) {
        return usersDao.findTrueName(username);
    }

    @Override
    public Integer findClssIdByName(String username) {
        return usersDao.findClassIdByName(username);
    }
}
