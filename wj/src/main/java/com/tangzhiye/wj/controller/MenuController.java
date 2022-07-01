package com.tangzhiye.wj.controller;

import com.tangzhiye.wj.pojo.AdminMenu;
import com.tangzhiye.wj.service.AdminMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {
    @Autowired
    private AdminMenuService adminMenuService;

    @GetMapping("/api/menu")
    public List<AdminMenu> menu(){
//    public String menu(){
        return adminMenuService.getMenusByCurrentUser();
    }
//    @GetMapping("/api/menu")
//    public String menu(){
//        return "test";
//    }
}
