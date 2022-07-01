package com.tangzhiye.wj.service;

import com.tangzhiye.wj.dao.AdminUserRoleDao;
import com.tangzhiye.wj.pojo.AdminUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserRoleService {
    @Autowired
    private AdminUserRoleDao adminUserRoleDao;

    public List<AdminUserRole> listAllByUid(int uid){
        return adminUserRoleDao.findAllByUid(uid);
    }
}
