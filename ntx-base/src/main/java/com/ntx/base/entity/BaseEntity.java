package com.ntx.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.ntx.base.enums.DeleteStatusEnum;
import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {

    //主键id
    private String id;

    //数据删除状态(0:未删除 -1:已删除)
    private int deleteStatus = DeleteStatusEnum.UNDELETE.getCode();

    //创建时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime = new Date();

    //更新时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime = new Date();

    //创建人
    private String createUserId;

    //更新人
    private String updateUserId;


}
