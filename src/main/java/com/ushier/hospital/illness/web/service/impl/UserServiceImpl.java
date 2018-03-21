package com.ushier.hospital.illness.web.service.impl;

import com.ushier.hospital.illness.web.bean.LayUITableBean;
import com.ushier.hospital.illness.web.bean.ResponseMessageBean;
import com.ushier.hospital.illness.web.entity.UserEntity;
import com.ushier.hospital.illness.web.global.ServerCode;
import com.ushier.hospital.illness.web.global.SessionKey;
import com.ushier.hospital.illness.web.mapper.UserMapper;
import com.ushier.hospital.illness.web.service.UserService;
import com.ushier.hospital.illness.web.util.DateUtil;
import com.ushier.hospital.illness.web.util.MD5Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public int insert(UserEntity userEntity) {

        int rows = 0;

        Date date = DateUtil.getCurrentDate();
        userEntity.setCreateTime(date);
        userEntity.setUpdateTime(date);

        userEntity.setPassword(MD5Encoder.encode(userEntity.getPassword()));

        try{
            rows = this.userMapper.insert(userEntity);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return rows;
    }

    @Override
    public int update(UserEntity userEntity) {
        int rows = 0;

        Date date = DateUtil.getCurrentDate();
        userEntity.setUpdateTime(date);

        try{
            rows = this.userMapper.update(userEntity);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return rows;
    }

    @Override
    public int delById(Integer id) {
        int rows = 0;

        try{
            rows = this.userMapper.delById(id);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return rows;
    }

    @Override
    public UserEntity queryById(Integer id) {

        try{
            return this.userMapper.queryById(id);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return null;
    }

    @Override
    public ResponseMessageBean<Boolean> login(String name, String password, HttpServletRequest request) {

        ResponseMessageBean<Boolean> bean = new ResponseMessageBean<>();
        try{
            UserEntity loginUser = this.userMapper.queryByName(name);

            if(null == loginUser){
                // No such user
                bean.setStatus(ServerCode.RETURN_FAIL);
                bean.setMsg(ServerCode.MSG_NO_SUCH_USER);
                bean.setData(false);
                return bean;
            }

            if(!MD5Encoder.encode(password).equals(loginUser.getPassword())){
                bean.setStatus(ServerCode.RETURN_FAIL);
                bean.setMsg(ServerCode.MSG_USERNAME_OR_PASSWORD_ERROR);
                bean.setData(false);
                return bean;
            }

            request.getSession().setAttribute(SessionKey.UID, loginUser.getId());
            request.getSession().setAttribute(SessionKey.RID, loginUser.getRoleId());
            request.getSession().setAttribute(SessionKey.HOS_ID, loginUser.getHosId());
            bean.setStatus(ServerCode.RETURN_OK);
            bean.setMsg(ServerCode.MSG_LOGIN_SUCCESS);
            bean.setData(true);

        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            bean.setStatus(ServerCode.RETURN_ERROR);
            bean.setMsg(ServerCode.MSG_SERVER_ERROR);
            bean.setData(false);
        }


        return bean;
    }

    @Override
    public LayUITableBean<UserEntity> pageQuery(Integer position, Integer length, Integer roleId, Integer hosId, String filterValue) {

        LayUITableBean<UserEntity> bean = new LayUITableBean<>();

        try{
            List<UserEntity> list = this.userMapper.pageQuery(position, length, roleId, hosId, filterValue);

            int count = this.userMapper.queryCount(roleId, hosId, filterValue);

            bean.setCode(ServerCode.LAYUI_RETURN_OK);
            bean.setData(list);
            bean.setCount(count);

        }catch (Exception e){
            e.printStackTrace();
        }

        return bean;
    }


}
