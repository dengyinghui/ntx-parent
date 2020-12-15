package com.ntx.base.service;

import com.ntx.base.enums.ConsumerStatusEnum;
import com.ntx.base.mapper.RocketmqMessageLogMapper;
import com.ntx.base.view.RocketmqMessageView;
import com.ntx.base.vo.RocketmqMessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RocketmqMessageLogService {

    @Autowired
    private RocketmqMessageLogMapper rocketmqMessageLogMapper;

    @Transactional
    public int save(RocketmqMessageVo rocketmqMessageVo){
        return rocketmqMessageLogMapper.save(rocketmqMessageVo);
    }

    public RocketmqMessageView findRocketmqMessageByMsgId(String msgId){
        return this.rocketmqMessageLogMapper.findRocketmqMessageByMsgId(msgId);
    }

    @Transactional
    public int updateStatusSuccess(String msgId){
        return this.rocketmqMessageLogMapper.updateStatus(msgId, ConsumerStatusEnum.CONSUMER_SUCCESS.getCode());
    }

    @Transactional
    public int updateStatusFail(String msgId){
        return this.rocketmqMessageLogMapper.updateStatus(msgId, ConsumerStatusEnum.CONSUMER_FAIL.getCode());
    }
}
