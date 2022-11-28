package com.wyl.service;

import com.wyl.pojo.Paper;

import java.util.List;

public interface PaperService {
    Integer addPaper(Paper paper);
    List<Paper> findPaperByEid(Integer eid);
}
