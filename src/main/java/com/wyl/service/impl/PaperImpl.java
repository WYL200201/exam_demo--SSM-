package com.wyl.service.impl;

import com.wyl.dao.PaperDao;
import com.wyl.pojo.Paper;
import com.wyl.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperImpl implements PaperService {
    @Autowired
    PaperDao paperDao;
    @Override
    public Integer addPaper(Paper paper) {
        return paperDao.addPaper(paper);
    }

    @Override
    public List<Paper> findPaperByEid(Integer eid) {
        return paperDao.findPaperByEid(eid);
    }
}
