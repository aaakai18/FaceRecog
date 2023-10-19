package com.example.service;

import com.example.dao.StuDao;
import com.example.pojo.StudentImg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StuService {

    @Autowired
    StuDao stuDao;

    public int save(StudentImg student)
    {
        int insert = stuDao.insert(student);
        return insert;
    }

    public String findById(Integer id)
    {
        StudentImg student = stuDao.selectById(id);
        return student.getImgData();
    }
}
