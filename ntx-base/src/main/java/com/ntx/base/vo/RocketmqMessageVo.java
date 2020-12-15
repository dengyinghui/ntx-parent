package com.ntx.base.vo;

import lombok.Data;

import java.util.Date;
@Data
public class RocketmqMessageVo {


    public RocketmqMessageVo(String msgId, String key) {
        this.msgId = msgId;
        this.key = key;
    }

    //消息id(全局唯一)
    private String msgId;
    //消息key
    private String key;
    //消息状态(0：处理中 1：消费成功 2：消费失败)
    private int status = 0;
    //创建时间
    private Date createTime = new Date();
}
