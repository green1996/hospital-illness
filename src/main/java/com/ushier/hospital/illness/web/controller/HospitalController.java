package com.ushier.hospital.illness.web.controller;

import com.ushier.hospital.illness.web.bean.LayUITableBean;
import com.ushier.hospital.illness.web.bean.ResponseMessageBean;
import com.ushier.hospital.illness.web.entity.HospitalEntity;
import com.ushier.hospital.illness.web.global.ServerCode;
import com.ushier.hospital.illness.web.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/hospital")
public class HospitalController {

    @Autowired
    private HospitalService service;

    @RequestMapping(value = "/pageQuery", method = RequestMethod.GET)
    public LayUITableBean<HospitalEntity> pageQuery(Integer currentPage, Integer limit) {

        int position = (currentPage - 1) * limit;

        return this.service.pageQuery(position, limit);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseMessageBean<?> save(HospitalEntity entity) {
        ResponseMessageBean<Boolean> bean = new ResponseMessageBean<>(ServerCode.RETURN_OK);
        int res = service.insert(entity);
        bean.setData(res > 0 ? true : false);
        return bean;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseMessageBean<?> update(@PathVariable(value = "id") Integer id, HospitalEntity entity) {
        ResponseMessageBean<Boolean> bean = new ResponseMessageBean<>(ServerCode.RETURN_OK);
        int res = service.update(entity);
        bean.setData(res > 0 ? true : false);
        bean.setMsg(res > 0 ? ServerCode.MSG_UPDATE_SUCCESS: ServerCode.MSG_UPDATE_FAIL);
        return bean;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseMessageBean<?> query(@PathVariable(value = "id") Integer id) {
        ResponseMessageBean<HospitalEntity> bean = new ResponseMessageBean<>(ServerCode.RETURN_OK);
        HospitalEntity entity = service.queryById(id);
        bean.setData(entity);
        return bean;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseMessageBean<?> delete(@PathVariable(value = "id") Integer id) {
        ResponseMessageBean<Boolean> bean = new ResponseMessageBean<>(ServerCode.RETURN_OK);
        int res = service.delById(id);
        bean.setData(res > 0 ? true : false);
        bean.setMsg(res > 0 ? ServerCode.MSG_DELETE_SUCCESS: ServerCode.MSG_DELETE_SUCCESS);
        return bean;
    }
}
