package com.wyl.dao;

import com.wyl.pojo.PClass;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PClassDao {
    //查询所有班级，就需要新建一个班级的Dao层，写一下查询所有班级的接口
    //接着创建与班级Dao层相关联的Mapper文件，写sql语句
    List<PClass> getAllClass();
    //查询班级
    PClass GetClassId(Integer classid);
}
