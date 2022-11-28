package com.wyl.service.impl;

import com.wyl.dao.SubjectDao;
import com.wyl.pojo.Subject;
import com.wyl.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    SubjectDao subjectDao;
    @Override
    public List<Subject> getAllSubject() {
        return subjectDao.getAllSubject();
    }

    @Override
    public Integer addSubject(Subject subject) {
        return subjectDao.addSubject(subject);
    }

    @Override
    public Integer updateSubject(Subject subject) {
        return subjectDao.updateSubject(subject);
    }

    @Override
    public Subject getById(Integer sid) {
        return subjectDao.getById(sid);
    }

    @Override
    public Integer delSingle(Integer sid) {
        return subjectDao.delSingle(sid);
    }

    @Override
    public List<Subject> getSubByCno(Integer cno) {
        return subjectDao.getSubByCno(cno);
    }
}
