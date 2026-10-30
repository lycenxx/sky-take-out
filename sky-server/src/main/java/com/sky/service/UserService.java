package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.vo.UserLoginVO;

/** 用户登录业务接口。 */
public interface UserService {

    /** 通过微信登录并查询或创建用户。 */
    User wxlogin(UserLoginDTO userLoginDTO);
}
