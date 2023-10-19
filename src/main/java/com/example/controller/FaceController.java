package com.example.controller;

import cn.hutool.json.JSONObject;
import com.example.Result;
import com.example.faceconfig.FaceMatch;
import com.example.pojo.StudentImg;
import com.example.service.StuService;
import com.example.utils.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class FaceController {

    @Autowired
    FaceMatch match;
    @Autowired
    StuService stuService;
    @PostMapping("/1")
    public Result test(@RequestBody String data)
    {
        //解析data数据
        int commaIndex = data.indexOf(",");
        if (commaIndex != -1) {
            data = data.substring(commaIndex + 1);
        }

        // 解码Base64数据
        //byte[] imageBytes = Base64.getDecoder().decode(data);

        Picture pp = new Picture();
        pp.setImage(data);
        pp.setImage_type("BASE64");
        //System.out.println("Bytes = "+imageBytes);
//        // 将图像字节数组保存到文件（可选）
//        try (FileOutputStream fos = new FileOutputStream("decoded_image.jpg")) {
//            fos.write(imageBytes);
//            System.out.println("图像已解码并保存到文件 decoded_image.jpg");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        String s = match.faceMatch(pp);

        JSONObject jsonObject = new JSONObject(s);
        System.out.println("s = "+s);

        return new Result(200,jsonObject,"成功");
    }

    //学生注册人脸信息
    @PostMapping("/add")
    public Result addImg(@RequestBody String studentImg){
        System.out.println(studentImg);
        //stuService.save(studentImg);
        return new Result(200,null ,"successfully");
    }

    //学生注册人脸信息
    @PostMapping("/add2")
    public Result addImg2(@RequestBody StudentImg studentImg){
        System.out.println(studentImg);
        //stuService.save(studentImg);
        return new Result(200,null ,"successfully");
    }
}
