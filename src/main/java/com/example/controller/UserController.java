package com.example.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.Result;
import com.example.pojo.Teacher;
import com.example.pojo.User;
import com.example.service.TeaService;
import com.example.service.UserService;
import com.example.utils.TokenUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    UserService userService;

    @Autowired
    TeaService teaService;

    @PostMapping("/1")
    public Result insertOne(@RequestBody User user){

        if (user.getUsername().equals(""))
            user.setUsername(null);
        System.out.println(user);
        return new Result(222,new Object());
    }


    @GetMapping("/page")
    public Result selectPage(@RequestParam Integer currentPage,
                             @RequestParam Integer pageSize,
                             @RequestParam(defaultValue = "") String id,
                             @RequestParam (defaultValue = "") String username,
                             @RequestParam (defaultValue = "") String admin)
    {
        User user = new User();

        if(id.equals(""))
        {
            user.setId(null);
        }else {
            user.setId(Integer.parseInt(id));
        }
        if(admin.equals(""))
        {
            user.setAdmin(null);
        }else {
            user.setAdmin(Integer.parseInt(admin));
        }

        if(username.equals(""))
        {
            user.setUsername(null);
        }else {
            user.setUsername(username);
        }
        User user1 = TokenUtils.getUser(request);
        System.out.println("当前登录用户是  "+ user1);
        IPage page = userService.Page(currentPage, pageSize,user);
        return new Result(2000,page,"successfully!");
    }


    @GetMapping("/role")
    public Result getRole(){
        List<User> users = userService.selectAll();
        return new Result(200,users,"");
    }

    @GetMapping("/course/{id}")
    public Result lookCourse(@PathVariable Integer id){
        IPage<User> page = userService.findPage(1, 5, id);
        return new Result(200,page,"");
    }


    @GetMapping(value = "/selectCourse")
    public Result selectCourse(@RequestParam Integer id){
        IPage iPage = userService.selectCourse(id);
        System.out.println(iPage.getRecords());
        return new Result(200,iPage,"查询成功");
    }

    @PostMapping("/i")
    public Result insert(@RequestBody User user)
    {
        int i = 0;
        if(user.getId() == null) {
            System.out.println(user);
             i = userService.insertOne(user);
        }
        else {
             i = userService.updateOne(user);
        }


        return new Result(i,user,"successfully!");
    }

    @DeleteMapping("/d")
    public Result delete(@RequestParam Integer id)
    {
        userService.deleteOne(id);
        return new Result(2000,null,"successfully!");
    }

    @GetMapping("/username/{username}")
    public Result selectByUsername(@PathVariable String username){
        userService.selectOne(username);
        return new Result();
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user)
    {

        String msg = "";
        User login = new User();
        Teacher teacher = new Teacher();
        //判断账号密码是否为空
        if(StrUtil.isBlank(user.getUsername())||StrUtil.isBlank(user.getPassword()))
        {
            msg = "blank";
        }else {

            login = userService.isLogin(user);
            if(login.getId()!=null) {
                 msg = "successfully!";
                 System.out.println(user);
                return new Result(200,login,msg);
             }
             else msg = "fail";
        }
        //判断教师端登录
        if(msg.equals("fail")&&msg != "blank")
        {
            teacher.setName(user.getUsername()) ;
            teacher.setPassword(user.getPassword());
            teacher = teaService.isLogin(teacher);
            if(teacher.getId()!=null) {
                msg = "successfully!";
                System.out.println(teacher);
                return new Result(200,teacher,msg);
            }
            else msg = "fail";
        }


        return new Result(200,null,msg);
    }


    @PostMapping("/register")
    public Result register(@RequestBody User user)
    {
        user.setAdmin(0);
        userService.insertOne(user);
        System.out.println(user);
        return new Result(200,user,"注册成功！");
    }


    /**
     * 到处数据为excel文件格式
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping("/export")
    public Result exp(HttpServletResponse response) throws Exception {
        List<User> users = userService.selectAll();
        //导出至浏览器下载
        ExcelWriter wr = ExcelUtil.getWriter(true);
        wr.write(users);
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

    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
       // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
        List<User> users = reader.readAll(User.class);

//        // 方式2：忽略表头的中文，直接读取表的内容
//        List<List<Object>> list = reader.read(1);
//        List<User> users = CollUtil.newArrayList();
//        for (List<Object> row : list) {
//            User user = new User();
//            user.setUsername(row.get(0).toString());
//            user.setPassword(row.get(1).toString());
//            user.setNickname(row.get(2).toString());
//            user.setEmail(row.get(3).toString());
//            user.setPhone(row.get(4).toString());
//            user.setAddress(row.get(5).toString());
//            user.setAvatarUrl(row.get(6).toString());
//            users.add(user);}


        userService.insertBatch(users);
        return new Result(2000,users,"true!");
    }








}
