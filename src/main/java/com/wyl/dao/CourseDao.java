package com.wyl.dao;

import com.wyl.pojo.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseDao {
    Course getAllById(Integer cno);
    List<Course> getAllCourse();

}
