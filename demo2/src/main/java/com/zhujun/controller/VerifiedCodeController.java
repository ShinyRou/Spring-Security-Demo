package com.zhujun.controller;

import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * <p>
 * Description: [获取验证码Controller]
 * </p>
 * Created on 2021/3/15 16:33
 *
 * @author <a href="mailto: "></a> zhujun
 * @version 1.0
 * Copyright (c) 2021
 **/
@Slf4j
@RequestMapping("/verifiedCode")
@Controller
public class VerifiedCodeController {
    @Autowired
    private Producer producer;


    @GetMapping("/getVerifiedCode")
    public void getVerifiedCode(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("image/jpeg");
        //创建验证码文本
        String text = producer.createText();
        //文本添加到session
        log.info("refresh code,seesion_id:{},code:{}",request.getSession().getId(),text);
        request.getSession().setAttribute("code",text);
        //创建图片
        BufferedImage img = producer.createImage(text);
        //获取输出流
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            ImageIO.write(img,"jpg",out);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out != null ){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
