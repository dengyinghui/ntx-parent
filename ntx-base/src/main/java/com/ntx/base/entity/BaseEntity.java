package com.ntx.base.entity;

import com.ntx.base.util.SnowFlakeUtil;

import java.util.Date;

public class BaseEntity {

    //主键id
    private String id = SnowFlakeUtil.getId();
    //数据是否删除标识(0:未删除 -1:已删除)
    private int deleteFlag = 0;
    //创建时间
    private Date createTime = new Date();
    //更新时间
    private Date updateTime = new Date();
    //创建人
    private String createUserId;
    //更新人
    private String updateUserId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }
}
