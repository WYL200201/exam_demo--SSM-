package com.wyl.service;

import com.wyl.pojo.StuScore;

import java.util.List;

public interface StuScoreService {
    Integer addStuScore(StuScore stuScore);
    List<StuScore> findStuScore(Integer userid);
    List<StuScore> getIfDone(Integer userid,Integer eid);
    List<StuScore> getStuScoreByClassid(Integer classid);
}
