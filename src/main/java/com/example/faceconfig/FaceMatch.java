package com.example.faceconfig;

import com.example.exception.ServiceException;
import com.example.service.StuService;
import com.example.utils.Encode;
import com.example.utils.GsonUtils;
import com.example.utils.HttpUtil;
import com.example.utils.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
@Configuration
public class FaceMatch {
    @Autowired
    Encode Code;

    @Autowired
    StuService stuService;



    public  String faceMatch(Picture PP) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/match";
        Picture PP2 = new Picture();

        try {

            //PP2 = code.make("face5.jpg");
            String ImgData = stuService.findById(1);
            PP2.setImage_type("BASE64");
            PP2.setImage(ImgData);
            List<Picture> Pictures = new ArrayList<>();
            Pictures.add(PP);
            Pictures.add(PP2);
            String param = GsonUtils.toJson(Pictures);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "[24.d3d0796a46800d836439993185de49d1.2592000.1700651147.282335-39096057]";

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println("result = "+result);
            return result;
        } catch (Exception e) {

        }
        return null;
    }
}
