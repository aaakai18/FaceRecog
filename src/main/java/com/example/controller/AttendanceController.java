package com.example.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.Result;
import com.example.pojo.SignIn;
import com.example.pojo.User;
import com.example.service.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
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
        //获取当前日期 精确到day
        Date currentDate = new Date();
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = simpleDateFormat.format(currentDate);

        List<SignIn> signIns = singInService.selectByCourseId(courseId, time);
        System.out.println(signIns);
        return new Result(200,signIns,"");
    }

    //导出签到表
    @GetMapping("/export/{courseId}")
    public Result exp(@PathVariable Integer courseId,HttpServletResponse response) throws Exception {
        //获取当前日期 精确到day
        Date currentDate = new Date();
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = simpleDateFormat.format(currentDate);
        List<SignIn> signIns = singInService.selectByCourseId(courseId, time);

        //导出至浏览器下载
        ExcelWriter wr = ExcelUtil.getWriter(true);
        wr.write(signIns);
        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        wr.flush(out, true);
        out.close();
        wr.close();

        return new Result(2000, null ,"successfully!");
    }



}
