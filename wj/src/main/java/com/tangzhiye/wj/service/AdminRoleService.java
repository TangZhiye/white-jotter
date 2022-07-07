package com.tangzhiye.wj.service;

import com.tangzhiye.wj.dao.AdminRoleDao;
import com.tangzhiye.wj.pojo.AdminRole;
import com.tangzhiye.wj.pojo.AdminUserRole;
import jdk.internal.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminRoleService {

    @Autowired
    private AdminRoleDao adminRoleDao;
    @Autowired
    private AdminUserRoleService adminUserRoleService;

    public List<AdminRole> ListRolesByUser(int uid){
        List<Integer> rids = adminUserRoleService.listAllByUid(uid).stream()
                .map(AdminUserRole::getRid).collect(Collectors.toList());
        List<AdminRole> roles = adminRoleDao.findAllById(rids);
        return roles;
    }
}
