package com.hlc.manager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.hlc.manager.entity.User;
import com.hlc.manager.service.UserService;
import com.hlc.manager.util.BaseController;
import com.hlc.manager.util.JsonResult;
import com.hlc.manager.util.PageResult;
import com.hlc.manager.util.StringUtil;
import com.wf.captcha.utils.CaptchaUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author rjyx
 * @Description
 * @Date create in 2018/8/10
 * @Modify by
 */
@Controller
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult login(String username, String password, String code, HttpServletRequest request) {
//        if (StringUtil.isBlank(username, password)) {
//            return JsonResult.error("账号密码不能为空");
//        }
//        if (!CaptchaUtil.ver(code, request)) {
//            CaptchaUtil.clear(request);
//            return JsonResult.error("验证码不正确");
//        }
        try {
            UsernamePasswordToken token = new UsernamePasswordToken("admin", "123456");
//            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            SecurityUtils.getSubject().login(token);
//            addLoginRecord(getLoginUserId(), request);
            return JsonResult.ok("登录成功");
        } catch (IncorrectCredentialsException ice) {
            return JsonResult.error("密码错误");
        } catch (UnknownAccountException uae) {
            return JsonResult.error("账号不存在");
        } catch (LockedAccountException e) {
            return JsonResult.error("账号被锁定");
        } catch (ExcessiveAttemptsException eae) {
            return JsonResult.error("操作频繁，请稍后再试");
        }
    }

    @RequestMapping("/user/list")
    @ResponseBody
    public  PageResult<User> findUser(Integer pageNum, Integer pageSize,String userName) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userService.getUsers(userName);
        PageResult<User> pageResult = new PageResult<>(list);
        return pageResult;
    }

    @GetMapping("user/index")
    public String userIndex(){
        return "user/user/list";
    }
}
