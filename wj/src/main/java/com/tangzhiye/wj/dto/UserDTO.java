package com.tangzhiye.wj.dto;

import com.tangzhiye.wj.dto.base.OutputConverter;
import com.tangzhiye.wj.pojo.AdminRole;
import com.tangzhiye.wj.pojo.User;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
// 使用DTO层将数据传回前端，而不是直接返回POJO，因为要隐去实体的一些细节，比如salt字段
public class UserDTO implements OutputConverter<UserDTO, User> {

    private int id;

    private String username;

    private String name;

    private String phone;

    private String email;

    private boolean enabled;

    private List<AdminRole> roles;
}
