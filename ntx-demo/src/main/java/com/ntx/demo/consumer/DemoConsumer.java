package com.ntx.demo.consumer;

import com.ntx.base.service.RocketmqMessageLogService;
import com.ntx.common.util.SnowFlakeUtil;
import com.ntx.demo.service.DemoService;
import com.ntx.demo.vo.TestBodyVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
public class DemoConsumer {

    @Autowired
    private DemoService demoService;
    @Autowired
    private RocketmqMessageLogService rocketmqMessageLogService;

    @Transactional(rollbackFor = Exception.class)
    public ConsumeConcurrentlyStatus consumer(String msgId, String messageBody){
        System.out.println(messageBody + "=============================" + Thread.currentThread().getName());
        TestBodyVo testBodyVo = new TestBodyVo();
        testBodyVo.setId(SnowFlakeUtil.getId());
        testBodyVo.setName("123");
        testBodyVo.setAge(41);
        demoService.save(testBodyVo);
        rocketmqMessageLogService.updateStatusSuccess(msgId);
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }




}
