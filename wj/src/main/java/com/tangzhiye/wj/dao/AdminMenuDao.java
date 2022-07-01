package com.tangzhiye.wj.dao;

import com.tangzhiye.wj.pojo.AdminMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminMenuDao extends JpaRepository<AdminMenu,Integer> {
    List<AdminMenu> findAllById(int mid);
    List<AdminMenu> findAllByParentId(int parentId);
}
