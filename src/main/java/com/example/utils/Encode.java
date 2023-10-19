package com.example.utils;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
@Configuration
public class Encode {

    public Picture make(String location)
    {
        try {
            // 1. 读取图片文件
            File imageFile = new File(location);
            FileInputStream fileInputStream = new FileInputStream(imageFile);
            byte[] imageData = new byte[(int) imageFile.length()];
            fileInputStream.read(imageData);
            fileInputStream.close();

            // 2. 将图片数据编码为Base64
            String base64EncodedImage = Base64.getEncoder().encodeToString(imageData);

            // 3. 打印或保存Base64编码的图片数据
            //System.out.println(base64EncodedImage);

            Picture PP = new Picture();
            PP.setImage(base64EncodedImage);
            PP.setImage_type("BASE64");
            return  PP;
            // 如果需要，可以将base64EncodedImage保存到文件或传输给其他应用程序
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    }

