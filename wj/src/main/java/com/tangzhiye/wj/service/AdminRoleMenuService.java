package com.tangzhiye.wj.service;

import com.tangzhiye.wj.dao.AdminRoleMenuDao;
import com.tangzhiye.wj.pojo.AdminRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminRoleMenuService {
    @Autowired
    private AdminRoleMenuDao adminRoleMenuDao;

    public List<AdminRoleMenu> findAllByRid(List<Integer> rids){
        return adminRoleMenuDao.findAllByRidIn(rids);
    }

}
