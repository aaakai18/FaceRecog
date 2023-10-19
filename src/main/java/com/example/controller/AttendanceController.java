package com.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.Result;
import com.example.pojo.SignIn;
import com.example.service.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired
    SignInService singInService;
    @PostMapping("/1")
    public Result insertOne(@RequestBody SignIn course){
        Date currentDate = new Date();
        course.setAttendanceTime(currentDate);
        singInService.insertOne(course);

        return new Result(200,null,"success");
    }

    //返回签到情况
    @GetMapping("/2")
    public Result getSignInSituation(@RequestParam Integer courseId) throws ParseException {
        Date currentDate = new Date();
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = simpleDateFormat.format(currentDate);

        List<SignIn> signIns = singInService.selectByCourseId(courseId, time);
        System.out.println(signIns);
        return new Result(200,signIns,"");
    }


}
