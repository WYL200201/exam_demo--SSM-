package com.wyl.service;

import com.wyl.pojo.Subject;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface SubjectService {
    List<Subject> getAllSubject();
    Integer addSubject(Subject subject);
    Integer updateSubject(Subject subject);
    Subject getById(Integer sid);
    Integer delSingle(Integer sid);
    List<Subject> getSubByCno(Integer cno);
}
