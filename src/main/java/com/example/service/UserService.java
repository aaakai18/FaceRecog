package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.example.dao.UserDao;
import com.example.exception.ServiceException;
import com.example.pojo.User;

import com.example.utils.TokenUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public  User selectOne(String username)
    {
        LambdaQueryWrapper<User> wr = new LambdaQueryWrapper<>();
        wr.eq(User::getUsername,username);
        User user = userDao.selectOne(wr);
        return user;
    }

    public IPage selectCourse(Integer id){
        IPage p = new Page();

        return userDao.selectCourse(p,id);
    }


    /**
     * 根据某个条件查询
     * @param user
     * @return
     */
    public LambdaQueryWrapper<User> selectByCondition(User user)
    {
        LambdaQueryWrapper<User> wr = new LambdaQueryWrapper<>();
        String name = user.getUsername();
        String password = user.getPassword();
        Integer id = user.getId();
        Integer isAdmin = user.getAdmin();
        wr
            .eq(name!=null,User::getUsername,name)
            .eq(password!=null,User::getPassword,password)
            .eq(id!=null,User::getId,id)
            .eq(isAdmin!=null,User::getAdmin,isAdmin);

        return wr;
    }

    public IPage<User> findPage(Integer currentPage,Integer pageSize,Integer id)
    {

        IPage<User> page =new Page<>(currentPage,pageSize);
         page =  userDao.findPage(page,id);

        return page;
    }




    /**
     * 分页查询函数
     *
     * @return
     */
    public IPage Page(Integer CurrentPage, Integer PageSize,User user)
    {

        IPage p = new Page(CurrentPage,PageSize);
        LambdaQueryWrapper<User> wr = this.selectByCondition(user);
        p = userDao.selectPage(p, wr);
        System.out.println("当前页数据"+p.getRecords());
        return p;
    }


    public List<User> selectAll()
    {
        List<User> users = userDao.selectList(null);
        return users;
    }

    public int insertOne(User user)
    {

        int insert = 0;
        try {
            insert = userDao.insert(user);
        } catch (Exception e) {
            System.out.println("异常信息："+e);
            throw new ServiceException(500,"用户名已存在");
        }
        return insert;
    }

    public int updateOne(User user)
    {

        int i = userDao.updateById(user);
        return i;
    }


    public int deleteOne(Integer id)
    {
        int i = userDao.deleteById(id);
        return i;
    }


    public Boolean insertBatch(List<User>users)
    {
        for(int i=0; i<users.size();i++)
        {
            userDao.insert(users.get(i));
        }

        return true;
    }


    public User isLogin(User user)
    {
        String username = user.getUsername();
        String password = user.getPassword();
        LambdaQueryWrapper<User> wr = new LambdaQueryWrapper<>();
        wr.eq(User::getUsername,username);
        wr.eq(User::getPassword,password);
        List<User> users = userDao.selectList(wr);
        System.out.println("users: "+users);
        if(users.size()!=0)
        {
            String token = TokenUtils.createToken(user.getUsername(),user.getPassword());
            users.get(0).setToken(token);
            return users.get(0);
        }
        else {
            return user;
        }
    }









}
