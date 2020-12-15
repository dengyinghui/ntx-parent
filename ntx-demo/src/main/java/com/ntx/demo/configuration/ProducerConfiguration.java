package com.ntx.demo.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@Slf4j
public class ProducerConfiguration {

    @Value("${rocketmq.nameServer}")
    private String namesrvAddr;

    private String groupName = "test-groupName";

    /**
     * 创建普通消息发送者实例
     */
    @Bean
    public DefaultMQProducer defaultProducer() throws MQClientException {
        log.info("开始启动" + groupName + "生成组 =======================");
        DefaultMQProducer producer = new DefaultMQProducer(groupName);
        producer.setNamesrvAddr(namesrvAddr);
        producer.setVipChannelEnabled(false);
        //消息发送失败重试次数
        producer.setRetryTimesWhenSendFailed(5);
        //异步消息发送失败重试次数
        producer.setRetryTimesWhenSendAsyncFailed(5);
        producer.start();
        log.info("生产组" + groupName + "启动成功 =======================");
        return producer;
    }

}
