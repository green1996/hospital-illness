package com.ushier.hospital.illness.web.service;

import com.ushier.hospital.illness.web.bean.LayUITableBean;
import com.ushier.hospital.illness.web.bean.ResponseMessageBean;
import com.ushier.hospital.illness.web.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    int insert(UserEntity userEntity);

    int update(UserEntity userEntity);

    int delById(Integer id);

    UserEntity queryById(Integer id);

    ResponseMessageBean<Boolean> login(String name, String password, HttpServletRequest request);

    LayUITableBean<UserEntity> pageQuery(Integer position, Integer length, Integer roleId, Integer hosId, String filterValue);

}
