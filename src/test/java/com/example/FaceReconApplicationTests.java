package com.example;

import com.example.service.SignInService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class FaceReconApplicationTests {


    SignInService signInService = new SignInService();
    @Test
    void byCourseId(){
        Date date = new Date();
        System.out.println(date);
    }
}
