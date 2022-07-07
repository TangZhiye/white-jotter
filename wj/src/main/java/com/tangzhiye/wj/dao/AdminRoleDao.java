package com.tangzhiye.wj.dao;

import com.tangzhiye.wj.pojo.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRoleDao extends JpaRepository<AdminRole, Integer> {
    AdminRole findById(int id);
    List<AdminRole> findAllById(List<Integer> id);
}
