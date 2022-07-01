package com.tangzhiye.wj.service;

import com.tangzhiye.wj.dao.AdminMenuDao;
import com.tangzhiye.wj.pojo.AdminMenu;
import com.tangzhiye.wj.pojo.AdminRoleMenu;
import com.tangzhiye.wj.pojo.AdminUserRole;
import com.tangzhiye.wj.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminMenuService {
    @Autowired
    private UserService userService;

    @Autowired
    private AdminUserRoleService userRoleService;

    @Autowired
    private AdminRoleMenuService adminRoleMenuService;

    @Autowired
    private AdminMenuDao adminMenuDao;

    // 根据当前用户查询出所有菜单项
    public List<AdminMenu> getMenusByCurrentUser(){
        // 使用shiro从数据库中获取当前用户
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.getByName(username);

        // 获得当前用户对应的所有角色id列表
        List<Integer> rids = userRoleService.listAllByUid(user.getId())
                .stream().map(AdminUserRole::getRid).collect(Collectors.toList());

        // 获得这些角色id对应的菜单
        List<Integer> mids = adminRoleMenuService.findAllByRid(rids)
                .stream().map(AdminRoleMenu::getMid).distinct().collect(Collectors.toList());
        List<AdminMenu> menus = adminMenuDao.findAllById(mids).stream().distinct().collect(Collectors.toList());

        // 处理菜单项的结构
        handleMenus(menus);

        return menus;
    }

    // 整合查询出的菜单数据
    public void handleMenus(List<AdminMenu> menus){
        // 遍历菜单项，根据每一项的 id 查询出该项所有的子项，并放进 children 属性
        menus.forEach(m -> {
            List<AdminMenu> children = getAllByParentId(m.getId());
            m.setChildren(children);
        });

        // 剔除掉所有子项，只保留第一层的父项。比如 c 是 b 的子项，b 是 a 的子项，我们最后只要保留 a 就行，因为 a 包含了 b 和 c
        // 也就是只保留 首页 和 最高项
        menus.removeIf(m -> m.getParentId() != 0);
    }

    public List<AdminMenu> getAllByParentId(int parentId) {
        return adminMenuDao.findAllByParentId(parentId);
    }
}
