package com.tangzhiye.wj.controller;

import com.tangzhiye.wj.dto.UserDTO;
import com.tangzhiye.wj.pojo.User;
import com.tangzhiye.wj.result.Result;
import com.tangzhiye.wj.result.ResultFactory;
import com.tangzhiye.wj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/admin/user")
    public Result list(){
        return ResultFactory.buildSuccessResult(userService.list());
    }

    @PutMapping("/api/admin/user/status")
    public Result updateUserStatus(@RequestBody User user){
        userService.updateUserStatus(user.getId(), user.isEnabled());
        return ResultFactory.buildSuccessResult("用户状态更新成功");
    }
}
