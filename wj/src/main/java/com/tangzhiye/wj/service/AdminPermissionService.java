package com.tangzhiye.wj.service;

import com.tangzhiye.wj.dao.AdminPermissionDao;
import com.tangzhiye.wj.dao.AdminRoleDao;
import com.tangzhiye.wj.dao.AdminRolePermissionDao;
import com.tangzhiye.wj.pojo.AdminPermission;
import com.tangzhiye.wj.pojo.AdminRole;
import com.tangzhiye.wj.pojo.AdminRolePermission;
import com.tangzhiye.wj.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminPermissionService {
    @Autowired
    private AdminPermissionDao adminPermissionDao;
    @Autowired
    private AdminRolePermissionDao adminRolePermissionDao;
    @Autowired
    private AdminRoleService adminRoleService;
    @Autowired
    private UserService userService;

    // 根据当前用户获取所有权限的方法
    public Set<String> listPermissionURLsByUser(String username){
        int uid = userService.getByName(username).getId();

        List<Integer> rids = adminRoleService.ListRolesByUser(uid)
                .stream().map(AdminRole::getId).collect(Collectors.toList());

        List<Integer> pids = adminRolePermissionDao.findAllByRid(rids)
                .stream().map(AdminRolePermission::getPid).collect(Collectors.toList());

        Set<String> URLs = adminPermissionDao.findAllById(pids)
                .stream().map(AdminPermission::getUrl).collect(Collectors.toSet());

        return URLs;
    }

    // 判断用户请求接口的是否在权限列表中
    public boolean needFilter(String requestAPI){
        List<AdminPermission> allPermissions = adminPermissionDao.findAll();
        for (AdminPermission permission: allPermissions){
            if (permission.getUrl().equals(requestAPI)){
                return true;
            }
        }
        return false;
    }
}
