package com.tangzhiye.wj.service;

import com.tangzhiye.wj.dao.UserDao;
import com.tangzhiye.wj.dto.UserDTO;
import com.tangzhiye.wj.pojo.AdminRole;
import com.tangzhiye.wj.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserDao userDao;
    @Autowired
    AdminUserRoleService adminUserRoleService;
    @Autowired
    AdminRoleService adminRoleService;

    public void updateUserStatus(int uid, boolean enabled){
        User user = userDao.getOne(uid);
        user.setEnabled(enabled);
        userDao.save(user);
    }

    /**
     *
     * @return 返回所有用户
     */
    public List<UserDTO> list(){
        List<UserDTO> userDTOs = userDao.findAll().stream()
                .map(user -> (UserDTO) new UserDTO().convertFrom(user)).collect(Collectors.toList());
        userDTOs.forEach(userDTO -> {
            List<AdminRole> roles = adminRoleService.ListRolesByUser(userDTO.getId());
            userDTO.setRoles(roles);
        });
        return userDTOs;
    }

    public boolean isExist(String username){
        User user = getByName(username);
        return null!=user;
    }

    public User getByName(String username){
        return userDao.findByUsername(username);
    }

    public User get(String username, String password){
        return userDao.getByUsernameAndPassword(username,password);
    }

    public void add(User user){
        userDao.save(user);
    }
}
