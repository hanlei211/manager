package com.hlc.manager.controller;

import com.github.pagehelper.PageHelper;
import com.hlc.manager.util.BaseController;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author rjyx
 * @Description
 * @Date create in 2018/8/9
 * @Modify by
 */
@Controller
@RequestMapping
public class IndexController  extends BaseController {

    @RequestMapping("/home")
    private ModelAndView index(){
        PageHelper.startPage(1,1);
        return new ModelAndView("home");
    }

    @GetMapping("/login")
    @ResponseBody
    private ModelAndView login(){
        return new ModelAndView("login");
    }

    @GetMapping("/index")
    @ResponseBody
    private ModelAndView index1(){
        return new ModelAndView("index");
    }


    /**
     * 图形验证码，用assets开头可以排除shiro拦截
     */
    @RequestMapping("/images/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        try {
//            CaptchaUtil.out(4, request, response);
            CaptchaUtil.outPng(4,request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
