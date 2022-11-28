package com.wyl.service;

import com.wyl.pojo.Exam;

import java.util.List;

public interface ExamService {
    Integer addExam(Exam exam);
    List<Exam> allExam();
    Exam findExamByEid(Integer eid);
    Integer exitExam(Exam exam);
    Integer deleExam(Integer eid);
    List<Exam> getExamList(Integer classid);
}
