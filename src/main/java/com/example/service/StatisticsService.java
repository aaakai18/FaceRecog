package com.example.service;

import com.example.dao.StatisticsDao;
import com.example.pojo.SignInStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {
    @Autowired
    StatisticsDao statisticsDao;

    public List<SignInStatistics> selectByCourseId(Integer courseId){
        return statisticsDao.selectByCourseId(courseId);
    }
}
