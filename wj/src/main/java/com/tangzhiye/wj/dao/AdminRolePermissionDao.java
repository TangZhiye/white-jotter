package com.tangzhiye.wj.dao;

import com.tangzhiye.wj.pojo.AdminRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRolePermissionDao extends JpaRepository<AdminRolePermission, Integer> {
    public List<AdminRolePermission> findAllByRid(List<Integer> rids);
}
