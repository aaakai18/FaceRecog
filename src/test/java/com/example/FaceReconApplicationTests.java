package com.example;

import com.example.pojo.SignInStatistics;
import com.example.service.SignInService;
import com.example.service.StatisticsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

//解决导入websocket后，测试出现异常问题
//因为WebSocket与Tomcat冲突，导致测试类报IllegalStateException
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class FaceReconApplicationTests {


    SignInService signInService = new SignInService();
    @Test
    void byCourseId(){
        System.out.println("hello");
    }

    @Autowired
    StatisticsService statisticsService;
    @Test
    void test(){
        List<SignInStatistics> signInStatistics = statisticsService.selectByCourseId(1);
        System.out.println(signInStatistics);
    }
}
