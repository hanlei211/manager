package com.hlc.manager.controller;

import com.hlc.manager.entity.User;
import com.hlc.manager.service.UserService;
import com.hlc.manager.util.IStatusMessage;
import com.hlc.manager.util.Result;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author rjyx
 * @Description
 * @Date create in 2018/8/10
 * @Modify by
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(User user) {
        Result result = new Result();
        result.setCode(IStatusMessage.SystemStatus.ERROR.getCode());
        if (null == user) {
            result.setCode(IStatusMessage.SystemStatus.PARAM_ERROR
                    .getCode());
            result.setMsg("请求参数有误，请您稍后再试");
            logger.debug("用户登录，结果=result:" + result);
            return result;
        }
        User existUser = userService.findUserByName(user.getUsername());
        if (existUser == null) {
            result.setMsg("该用户不存在，请您联系管理员");
            logger.debug("用户登录，结果=result:" + result);
            return result;
        } else {

            // 用户登录
            try {
                // 1、 封装用户名、密码、是否记住我到token令牌对象 [支持记住我]
                AuthenticationToken token = new UsernamePasswordToken(
                        user.getUsername(), DigestUtils.md5Hex(user.getPassword()));
                // 2、 Subject调用login
                Subject subject = SecurityUtils.getSubject();
                // 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
                // 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
                // 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
                logger.debug("用户登录，用户验证开始！user=" + user.getUsername());
                subject.login(token);
                result.setCode(IStatusMessage.SystemStatus.SUCCESS
                        .getCode());
                logger.info("用户登录，用户验证通过！user=" + user.getUsername());
            } catch (UnknownAccountException uae) {
                logger.error("用户登录，用户验证未通过：未知用户！user=" + user.getUsername(), uae);
                result.setMsg("该用户不存在，请您联系管理员");
            } catch (IncorrectCredentialsException ice) {
                // 获取输错次数
                logger.error("用户登录，用户验证未通过：错误的凭证，密码输入错误！user=" + user.getUsername(),
                        ice);
                result.setMsg("用户名或密码不正确");
            } catch (LockedAccountException lae) {
                logger.error("用户登录，用户验证未通过：账户已锁定！user=" + user.getUsername(), lae);
                result.setMsg("账户已锁定");
            } catch (ExcessiveAttemptsException eae) {
                logger.error(
                        "用户登录，用户验证未通过：错误次数大于5次,账户已锁定！user=.getMobile()" + user, eae);
                result.setMsg("用户名或密码错误次数大于5次,账户已锁定!</br><span style='color:red;font-weight:bold; '>2分钟后可再次登录，或联系管理员解锁</span>");
                // 这里结合了，另一种密码输错限制的实现，基于redis或mysql的实现；也可以直接使用RetryLimitHashedCredentialsMatcher限制5次
            } catch (AuthenticationException ae) {
                // 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
                logger.error("用户登录，用户验证未通过：认证异常，异常信息如下！user=" + user.getMobile(),
                        ae);
                result.setMsg("用户名或密码不正确");
            } catch (Exception e) {
                logger.error("用户登录，用户验证未通过：操作异常，异常信息如下！user=" + user.getMobile(), e);
                result.setMsg("用户登录失败，请您稍后再试");
            }
        }
        return result;
    }
}
