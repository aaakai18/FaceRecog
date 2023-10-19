package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dao.TeaDao;
import com.example.exception.ServiceException;
import com.example.pojo.Teacher;
import com.example.pojo.Teacher;
import com.example.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeaService {
    @Autowired
    TeaDao teaDao;

    public LambdaQueryWrapper<Teacher> selectByCondition(Teacher teacher)
    {
        LambdaQueryWrapper<Teacher> wr = new LambdaQueryWrapper<>();
        String name = teacher.getName();
        Integer status = teacher.getStatus();
        Integer id = teacher.getId();
        Integer isAdmin = teacher.getAdmin();
        String phone = teacher.getPhone();
        String password = teacher.getPassword();
        wr
                .eq(name!=null,Teacher::getName,name)
                .eq(status!=null,Teacher::getStatus,status)
                .eq(id!=null,Teacher::getId,id)
                .eq(isAdmin!=null,Teacher::getAdmin,isAdmin)
                .eq(phone!=null,Teacher::getPhone,phone)
                .eq(password!=null,Teacher::getPassword,password);

        return wr;
    }

    public IPage Page(Integer CurrentPage, Integer PageSize, Teacher teacher)
    {

        IPage p = new Page(CurrentPage,PageSize);
        LambdaQueryWrapper<Teacher> wr = this.selectByCondition(teacher);
        p = teaDao.selectPage(p, wr);
        System.out.println("当前页数据"+p.getRecords());
        return p;
    }

    public int insertOne(Teacher teacher)
    {

        int insert = 0;
        try {
            insert = teaDao.insert(teacher);
        } catch (Exception e) {
            System.out.println("异常信息："+e);
            throw new ServiceException(500,"用户名已存在");
        }
        return insert;
    }

    public int updateOne(Teacher teacher)
    {

        int i = teaDao.updateById(teacher);
        return i;
    }

    public List<Teacher> selectAll()
    {
        List<Teacher> teachers = teaDao.selectList(null);
        return teachers;
    }


    public IPage<Teacher> findPage(Integer currentPage,Integer pageSize,Integer id)
    {

        IPage<Teacher> page =new Page<>(currentPage,pageSize);
        page =  teaDao.findPage(page,id);

        return page;
    }

    public Teacher isLogin(Teacher teacher)
    {
        String name = teacher.getName();
        String password = teacher.getPassword();
        LambdaQueryWrapper<Teacher> wr = new LambdaQueryWrapper<>();
        wr.eq(Teacher::getName,name);
        wr.eq(Teacher::getPassword,password);
        List<Teacher> teachers = teaDao.selectList(wr);
        System.out.println("Teachers: "+teachers);
        if(teachers.size()!=0)
        {
            String token = TokenUtils.createToken(teacher.getName(),teacher.getPassword());
            teachers.get(0).setToken(token);
            return teachers.get(0);
        }
        else {
            return teacher;
        }
    }

}
