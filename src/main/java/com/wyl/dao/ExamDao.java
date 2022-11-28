package com.wyl.dao;

import com.wyl.pojo.Exam;
import org.apache.ibatis.annotations.Mapper;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Mapper
public interface ExamDao {
    Integer addExam(Exam exam);
    List<Exam> allExam();
    Exam getExamByEid(Integer eid);
    Integer updateExam(Exam exam);
    //要实现删除试卷信息，就要先实现删除paper
    Integer deleExam(Integer eid);
    //通过班级id查询exam
    List<Exam> getExamsByClassid(Integer classid);

}
