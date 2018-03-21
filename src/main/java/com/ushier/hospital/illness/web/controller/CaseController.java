package com.ushier.hospital.illness.web.controller;

import com.ushier.hospital.illness.web.bean.LayUITableBean;
import com.ushier.hospital.illness.web.bean.ResponseMessageBean;
import com.ushier.hospital.illness.web.dto.CaseDTO;
import com.ushier.hospital.illness.web.dto.SeasonSicknessDTO;
import com.ushier.hospital.illness.web.entity.CaseEntity;
import com.ushier.hospital.illness.web.global.ServerCode;
import com.ushier.hospital.illness.web.global.SessionKey;
import com.ushier.hospital.illness.web.global.SicknessEnum;
import com.ushier.hospital.illness.web.service.CaseService;
import com.ushier.hospital.illness.web.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping(value = "/case")
public class CaseController {

    private static final Logger logger = LoggerFactory.getLogger(CaseController.class);

    @Autowired
    private CaseService service;

    /**
     * 季节患病提醒
     * @return
     */
    @RequestMapping(value = "/seasonSickness", method = RequestMethod.GET)
    public Object seasonSickness(){
        Date startDate = DateUtil.getCurrentQuarterStartTime();
        Date endDate = DateUtil.getCurrentQuarterEndTime();
        return this.service.seasonSicknessList(startDate, endDate);
    }

    @RequestMapping(value = "/sickness", method = RequestMethod.GET)
    public Object sicknessList(){
        List<Map<String, Object>> list = new ArrayList<>();
        for(SicknessEnum se : SicknessEnum.values()){
            Map<String, Object> map = new HashMap<>(2);
            map.put("id", se.getId());
            map.put("desc", se.getDesc());
            list.add(map);
        }
        return list;
    }

    @RequestMapping(value = "/pageQuery", method = RequestMethod.GET)
    public LayUITableBean<CaseDTO> pageQuery(Integer currentPage, Integer limit,
                                             Integer did, Integer uid, HttpServletRequest request){

        HttpSession session = request.getSession();
        Integer rid = (Integer) session.getAttribute(SessionKey.RID);
        Integer hosId = (Integer) session.getAttribute(SessionKey.HOS_ID);
        Integer userId = (Integer) session.getAttribute(SessionKey.UID);
        if(rid == 2){
            did = userId;
        }else if(rid == 3){
            uid = userId;
        }

        int position = (currentPage - 1) * limit;

        return this.service.pageQuery(hosId, did, uid, "", position, limit);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseMessageBean<Boolean> save(CaseEntity caseEntity, HttpServletRequest request){
        ResponseMessageBean<Boolean> bean = new ResponseMessageBean<>(ServerCode.RETURN_OK);

        HttpSession session = request.getSession();
        Integer did = (Integer) session.getAttribute(SessionKey.UID);
        if(null == did){
            bean.setData(false);
            bean.setMsg("请登陆后操作");
            return bean;
        }
        caseEntity.setDid(did);

        int rows = this.service.insert(caseEntity);
        bean.setData(rows > 0 ? true : false);
        bean.setMsg(rows > 0 ? ServerCode.MSG_SAVE_SUCCESS : ServerCode.MSG_SAVE_FAIL);
        return bean;
    }

    @RequestMapping(value = "/saveForDoctor", method = RequestMethod.POST)
    public ResponseMessageBean<Boolean> saveForDoctor(CaseEntity caseEntity, HttpServletRequest request){
        ResponseMessageBean<Boolean> bean = new ResponseMessageBean<>(ServerCode.RETURN_OK);

        HttpSession session = request.getSession();
        Integer did = (Integer) session.getAttribute(SessionKey.UID);
        Integer hosId = (Integer) session.getAttribute(SessionKey.HOS_ID);
        if(null == did){
            bean.setData(false);
            bean.setMsg("请登陆后操作");
            return bean;
        }
        caseEntity.setDid(did);
        caseEntity.setHosId(hosId);

        int rows = this.service.insert(caseEntity);
        bean.setData(rows > 0 ? true : false);
        bean.setMsg(rows > 0 ? ServerCode.MSG_SAVE_SUCCESS : ServerCode.MSG_SAVE_FAIL);
        return bean;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseMessageBean<Boolean> update(CaseEntity caseEntity){
        ResponseMessageBean<Boolean> bean = new ResponseMessageBean<>(ServerCode.RETURN_OK);

        int rows = this.service.update(caseEntity);
        bean.setData(rows > 0 ? true : false);
        bean.setMsg(rows > 0 ? ServerCode.MSG_UPDATE_SUCCESS : ServerCode.MSG_UPDATE_FAIL);
        return bean;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseMessageBean<Boolean> delById(Integer id){
        ResponseMessageBean<Boolean> bean = new ResponseMessageBean<>(ServerCode.RETURN_OK);

        int rows = this.service.delById(id);
        bean.setData(rows > 0 ? true : false);
        bean.setMsg(rows > 0 ? ServerCode.MSG_DELETE_SUCCESS : ServerCode.MSG_DELETE_FAIL);
        return bean;
    }
}
