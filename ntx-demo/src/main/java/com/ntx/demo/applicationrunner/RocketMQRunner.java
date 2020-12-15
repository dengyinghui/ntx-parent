package com.ntx.demo.applicationrunner;

import com.ntx.common.util.ExceptionUtil;
import com.ntx.demo.constant.RocketMQKey;
import com.ntx.demo.consumer.DemoConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(1)
@Slf4j
public class RocketMQRunner implements ApplicationRunner {

    @Value("${rocketmq.nameServer}")
    private String namesrvAddr;


    public static final String CHARSET_NAME = "UTF-8";


    @Autowired
    private DemoConsumer demoConsumer;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(RocketMQKey.GROUP_NAME);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.subscribe(RocketMQKey.TOPIC, RocketMQKey.TAG);
        consumer.setMessageModel(MessageModel.CLUSTERING);  //集群消费模式
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for(MessageExt messageExt : msgs){
                    try{
                        String messageBody = new String(messageExt.getBody(), CHARSET_NAME);
                        return demoConsumer.consumer(messageBody);
                    } catch (Exception e){
                        e.printStackTrace();
                        log.info("消息[msgId:" + messageExt.getMsgId() + " === key:" + messageExt.getKeys() + "]字符解码失败:" + ExceptionUtil.getExceptionDetail(e));
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();

    }


}
