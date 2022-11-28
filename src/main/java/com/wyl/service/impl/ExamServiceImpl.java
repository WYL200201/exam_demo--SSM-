package com.wyl.service.impl;

import com.wyl.dao.ExamDao;
import com.wyl.dao.PaperDao;
import com.wyl.dao.StuScoreDao;
import com.wyl.pojo.Exam;
import com.wyl.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    ExamDao examDao;
    @Autowired
    //注入的原因在于要想删除掉exam的信息，就要先把paper的信息删除掉
    PaperDao paperDao;
    @Autowired
    StuScoreDao stuScoreDao;
    @Override
    public Integer addExam(Exam exam) {
        return examDao.addExam(exam);
    }

    @Override
    public List<Exam> allExam() {
        return examDao.allExam();
    }

    @Override
    public Exam findExamByEid(Integer eid) {
        return examDao.getExamByEid(eid);
    }

    @Override
    public Integer exitExam(Exam exam) {
        return examDao.updateExam(exam);
    }

    @Override
    public Integer deleExam(Integer eid) {
        stuScoreDao.deleteByEid(eid);
        paperDao.delPaper(eid);
        return examDao.deleExam(eid);
    }

    @Override
    public List<Exam> getExamList(Integer classid) {
        return examDao.getExamsByClassid(classid);
    }

}
