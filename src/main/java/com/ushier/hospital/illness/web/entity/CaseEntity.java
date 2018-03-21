package com.ushier.hospital.illness.web.entity;

import java.util.Date;

public class CaseEntity {
    private Integer id;
    private Integer uid;
    private Integer did;
    private Integer hosId;
    private String content;
    private Date createTime;
    private Integer sicknessId;
    private String recipel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public Integer getHosId() {
        return hosId;
    }

    public void setHosId(Integer hosId) {
        this.hosId = hosId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSicknessId() {
        return sicknessId;
    }

    public void setSicknessId(Integer sicknessId) {
        this.sicknessId = sicknessId;
    }

    public String getRecipel() {
        return recipel;
    }

    public void setRecipel(String recipel) {
        this.recipel = recipel;
    }
}
