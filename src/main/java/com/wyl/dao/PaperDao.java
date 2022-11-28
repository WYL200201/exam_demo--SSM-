package com.wyl.dao;

import com.wyl.pojo.Paper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaperDao {
    Integer addPaper(Paper paper);
    //根据eid查询paper的具体信息（情况
    List<Paper> findPaperByEid(Integer eid);
    //删除paper信息
    Integer delPaper(Integer eid);
}
