package com.hlc.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author rjyx
 * @Description
 * @Date create in 2018/8/9
 * @Modify by
 */
@Controller
@RequestMapping
public class IndexController {

    @GetMapping("/index")
    @ResponseBody
    private ModelAndView index(){
        return new ModelAndView("index");
    }

    @GetMapping("/login")
    @ResponseBody
    private ModelAndView login(){
        return new ModelAndView("login");
    }
}
