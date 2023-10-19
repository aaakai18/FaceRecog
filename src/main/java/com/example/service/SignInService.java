package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.dao.SignInDao;
import com.example.pojo.SignIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SignInService {
    @Autowired
    SignInDao signInDao;
    public void insertOne(SignIn course2){
        int insert = signInDao.insert(course2);
    }

    public List<SignIn> selectByCourseId(Integer courseId, String date){
        List<SignIn> signInIPage = signInDao.selectByCourseId(courseId, date);


        return  signInIPage;
    }
}
