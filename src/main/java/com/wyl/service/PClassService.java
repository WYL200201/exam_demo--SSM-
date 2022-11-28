package com.wyl.service;

import com.wyl.pojo.PClass;

import java.util.List;

public interface PClassService {
    List<PClass> getAllPClass();

    PClass getClassid(Integer classid);
}
