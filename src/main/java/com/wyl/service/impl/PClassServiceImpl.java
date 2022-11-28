package com.wyl.service.impl;

import com.wyl.dao.PClassDao;
import com.wyl.pojo.PClass;
import com.wyl.service.PClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PClassServiceImpl implements PClassService {
    @Autowired
    PClassDao pClassDao;
    @Override
    public List<PClass> getAllPClass(){
        return pClassDao.getAllClass();
        //返回controller层，将service进行注入
    }

    @Override
    public PClass getClassid(Integer classid) {
        return pClassDao.GetClassId(classid);
    }

    ;

}
