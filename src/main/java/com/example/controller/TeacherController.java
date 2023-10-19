package com.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.Result;
import com.example.pojo.Teacher;
import com.example.pojo.User;
import com.example.service.TeaService;
import com.example.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {


    @Autowired
    TeaService teaService;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @GetMapping("/page")
    public Result selectPage(@RequestParam Integer currentPage,
                             @RequestParam Integer pageSize,
                             @RequestParam(defaultValue = "") String id,
                             @RequestParam (defaultValue = "") String name,
                             @RequestParam (defaultValue = "") String admin)
    {
        Teacher teacher = new Teacher();

        if(id.equals(""))
        {
            teacher.setId(null);
        }else {
            teacher.setId(Integer.parseInt(id));
        }
        if(admin.equals(""))
        {
            teacher.setAdmin(null);
        }else {
            teacher.setAdmin(Integer.parseInt(admin));
        }

        if(name.equals(""))
        {
            teacher.setName(null);
        }else {
            teacher.setName(name);
        }

        IPage page = teaService.Page(currentPage, pageSize,teacher);
        return new Result(2000,page,"successfully!");
    }

    @PostMapping("/i")
    public Result insert(@RequestBody Teacher teacher)
    {
        int i = 0;
        System.out.println(teacher);
        if(teacher.getId() == null) {
            System.out.println(teacher);
            i = teaService.insertOne(teacher);
        }
        else {
            i = teaService.updateOne(teacher);
        }
        return new Result(i,teacher,"successfully!");
    }

    @GetMapping("/role")
    public Result getRole(){
        List<Teacher> teachers = teaService.selectAll();
        return new Result(200,teachers,"");
    }

    @GetMapping("/course/{id}")
    public Result lookCourse(@PathVariable Integer id){
        IPage<Teacher> page = teaService.findPage(1, 5, id);
        System.out.println(page.getRecords());
        return new Result(200,page,"");
    }
}
