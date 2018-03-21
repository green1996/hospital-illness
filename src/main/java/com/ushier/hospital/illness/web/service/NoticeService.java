package com.ushier.hospital.illness.web.service;


import com.ushier.hospital.illness.web.bean.LayUITableBean;
import com.ushier.hospital.illness.web.entity.NoticeEntity;

public interface NoticeService {

    LayUITableBean<NoticeEntity> pageQuery(Integer position, Integer length);

    int insert(NoticeEntity entity);

    int update(NoticeEntity entity);

    int delById(Integer id);

}
