package com.wyl.service;

import com.wyl.pojo.Course;

import java.util.List;

public interface CourseService {
    Course getAllById(Integer cno);
    List<Course> getAllCourse();
}
