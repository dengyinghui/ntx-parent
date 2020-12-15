package com.ntx.demo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
public class DemoConsumer {


    @Transactional(rollbackFor = Exception.class)
    public ConsumeConcurrentlyStatus consumer(String messageBody){
        System.out.println(messageBody + "=============================" + Thread.currentThread().getName());
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }




}
