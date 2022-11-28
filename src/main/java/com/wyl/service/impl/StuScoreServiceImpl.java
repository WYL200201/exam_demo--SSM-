package com.wyl.service.impl;

import com.wyl.dao.StuScoreDao;
import com.wyl.pojo.StuScore;
import com.wyl.service.StuScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StuScoreServiceImpl implements StuScoreService {
    @Autowired
    StuScoreDao stuScoreDao;
    @Override
    public Integer addStuScore(StuScore stuScore) {
        return stuScoreDao.addStuScore(stuScore);
    }

    @Override
    public List<StuScore> findStuScore(Integer userid) {
        return stuScoreDao.findStuScore(userid);
    }

    @Override
    public List<StuScore> getIfDone(Integer userid, Integer eid) {
        return stuScoreDao.getIfDone(userid,eid);
    }

    @Override
    public List<StuScore> getStuScoreByClassid(Integer classid) {
        return stuScoreDao.findStuScoreByClassid(classid);
    }
}
