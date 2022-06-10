package com.tanghziye.wj.controller;

import com.tanghziye.wj.pojo.User;
import com.tanghziye.wj.result.Result;
import com.tanghziye.wj.result.ResultFactory;
import com.tanghziye.wj.service.UserService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping("api/login")
    public Result login(@RequestBody User requestUser, HttpSession session){
        //对html标签进行转义，防止XSS攻击
        String username = HtmlUtils.htmlEscape(requestUser.getUsername());
        if (!userService.isExist(username)){return ResultFactory.buildFailResult("用户名不存在!");}

        String salt = userService.getByName(username).getSalt();
        int iterateTimes = 2;
        // 生成salt hash后的密码
        String saltPassword = new SimpleHash("md5",requestUser.getPassword(),salt,iterateTimes).toString();
        // 验证
        User user = userService.get(username, saltPassword);
        if (null == user){
//            return new Result(400);
            return ResultFactory.buildFailResult("账号或密码错误!");
        } else {
            session.setAttribute("user",user);
//            return new Result(200);
            return ResultFactory.buildSuccessResult(null);
        }
    }

    @PostMapping("/api/register")
    public Result register(@RequestBody User user){
        String username = HtmlUtils.htmlEscape(user.getUsername());
        if (userService.isExist(username)){
            return ResultFactory.buildFailResult("用户名已被使用!");
        } else {
            // 生成盐,默认长度 16 位
            String salt = new SecureRandomNumberGenerator().nextBytes().toString();
            int iterateTimes = 2;
            // 生成salt hash后的密码
            String saltPassword = new SimpleHash("md5",user.getPassword(),salt,iterateTimes).toString();
            // 存储密码和salt
            user.setSalt(salt);
            user.setPassword(saltPassword);
            userService.add(user);

            return ResultFactory.buildSuccessResult(user);
        }
    }
}
