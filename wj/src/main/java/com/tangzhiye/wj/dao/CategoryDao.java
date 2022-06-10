package com.tangzhiye.wj.dao;

import com.tangzhiye.wj.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category, Integer> {
}
