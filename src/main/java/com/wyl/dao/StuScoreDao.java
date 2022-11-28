package com.wyl.dao;

import com.wyl.pojo.StuScore;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StuScoreDao {
    Integer addStuScore(StuScore stuScore);
    //查询学生成绩
    List<StuScore> findStuScore(Integer userid);
    //查询学生是不是考过这个了
    List<StuScore> getIfDone(Integer userid,Integer eid);
    //教师查询试卷信息
    List<StuScore> findStuScoreByClassid(Integer classid);
    //清空考试信息
    Integer delateByUser(Integer userid);
    //根据考试id删除
    Integer deleteByEid(Integer eid);
}
