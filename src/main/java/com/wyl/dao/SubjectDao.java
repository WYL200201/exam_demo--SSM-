package com.wyl.dao;

import com.wyl.pojo.Subject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubjectDao {
    //查询题目列表
    List<Subject> getAllSubject();
    //添加题目
    Integer addSubject(Subject subject);
    Integer updateSubject(Subject subject);
    Subject getById(Integer sid);
    Integer delSingle(Integer sid);
    //通过课程id查询题
    List<Subject> getSubByCno(Integer cno);
}
