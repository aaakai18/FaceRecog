package com.example.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.Result;
import com.example.pojo.CourseData;
import com.example.pojo.SignIn;
import com.example.pojo.SignInStatistics;
import com.example.pojo.User;
import com.example.service.CourseService;
import com.example.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    CourseService courseService;

    @Autowired
    StatisticsService statisticsService;

    @PostMapping("/1")
    public Result insertOne(@RequestBody User user){

        if (user.getUsername().equals(""))
            user.setUsername(null);
        System.out.println(user);
        return new Result(200,new Object());
    }

    @GetMapping("/lookStudent/{id}")
    public Result lookStudent(@PathVariable Integer id)
    {
        IPage iPage = courseService.lookStudents(id);
        return new Result(200,iPage,"查询学生成功");
    }


    @GetMapping("/page")
    public Result selectPage(@RequestParam Integer currentPage,
                             @RequestParam Integer pageSize,
                             @RequestParam(defaultValue = "") String id,
                             @RequestParam (defaultValue = "") String name)
    {
        CourseData courseData = new CourseData();

        if(id.equals(""))
        {
            courseData.setId(null);
        }else {
            courseData.setId(Integer.parseInt(id));
        }

        if(name.equals(""))
        {
            courseData.setCrname(null);
        }else {
            courseData.setCrname(name);
        }

        IPage page = courseService.test(currentPage, pageSize,name);
        System.out.println(page.getRecords());
        return new Result(2000,page,"successfully!");
    }


    @PostMapping("/i")
    public Result insert(@RequestBody CourseData courseData)
    {
        int i = 0;
        //根据是否传入id 来区分是插入新数据还是更新数据
        if(courseData.getId() == null) {
            System.out.println(courseData);
             i = courseService.insertOne(courseData);
        }
        else {
             i = courseService.updateOne(courseData);
        }


        return new Result(i,courseData,"successfully!");
    }

    @DeleteMapping("/d")
    public Result delete(@RequestParam Integer id)
    {

        return new Result(2000,null,"successfully!");
    }

    //根据课程id统计签到情况
    @GetMapping("/statistics/{courseId}")
    public Result statisticsByCourseId(@PathVariable Integer courseId){
        List<SignInStatistics> signInStatistics = statisticsService.selectByCourseId(courseId);
        return new Result(200,signInStatistics,"OK");
    }

    //导出统计表
    @GetMapping("/export/{courseId}")
    public Result exp(@PathVariable Integer courseId,HttpServletResponse response) throws Exception {

        List<SignInStatistics> signInStatistics = statisticsService.selectByCourseId(courseId);

        //导出至浏览器下载
        ExcelWriter wr = ExcelUtil.getWriter(true);
        wr.write(signInStatistics);
        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("签到统计", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        wr.flush(out, true);
        out.close();
        wr.close();

        return new Result(200, null ,"successfully!");
    }


}
