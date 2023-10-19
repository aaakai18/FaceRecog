package com.example.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.pojo.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Configuration
public class TokenUtils {
    @Autowired
    private UserService userService;

    public static UserService staticService;

    @PostConstruct
    public void init()
    {
        staticService = userService;
    }


    /**
     * 生成token函数
     * @param id 用户id
     * @param sign 用户密码
     * @return
     */
    public static String createToken(String id,String sign)
    {
        return JWT.create().withAudience(id)
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2))
                .sign(Algorithm.HMAC256(sign));

    }

    //获取当前登录用户对象
    public static User getUser(HttpServletRequest request){
        try{
            String token = request.getHeader("token");
            //获取username
            String username = JWT.decode(token).getAudience().get(0);
            User user = staticService.selectOne(username);
            user.setToken(token);
            return user;
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }
}
