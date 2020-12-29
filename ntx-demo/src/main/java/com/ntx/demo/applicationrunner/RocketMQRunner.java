package com.ntx.demo.applicationrunner;

import com.ntx.base.enums.ConsumerStatusEnum;
import com.ntx.base.service.RocketmqMessageLogService;
import com.ntx.base.view.RocketmqMessageView;
import com.ntx.base.vo.RocketmqMessageVo;
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
    @Autowired
    private RocketmqMessageLogService rocketmqMessageLogService;

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
                    String messageBody = null;
                    try{
                        messageBody = new String(messageExt.getBody(), CHARSET_NAME);
                    } catch (Exception e){
                        e.printStackTrace();
                        log.info("消息[msgId:" + messageExt.getMsgId() + " === key:" + messageExt.getKeys() + "]字符解码失败:" + ExceptionUtil.getExceptionDetail(e));
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    String msgId = messageExt.getMsgId();
                    RocketmqMessageView rocketmqMessageView = rocketmqMessageLogService.findRocketmqMessageByMsgId(msgId);
                    if(rocketmqMessageView == null){
                        RocketmqMessageVo rocketmqMessageVo = new RocketmqMessageVo(msgId, messageExt.getKeys());
                        rocketmqMessageLogService.save(rocketmqMessageVo);
                        log.info("msgId:" + msgId + "开始消费.");
                        return demoConsumer.consumer(msgId, messageBody);
                    } else{
                        int status = rocketmqMessageView.getStatus();
                        if(status == ConsumerStatusEnum.CONSUMERING.getCode()){
                            //消费中
                            log.info("msgId:" + msgId + "消费中.");
                            return demoConsumer.consumer(msgId, messageBody);
                        } else if(status == ConsumerStatusEnum.CONSUMER_SUCCESS.getCode()){
                            //消费成功
                            log.info("msgId:" + msgId + "消费成功.");
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        } else{
                            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                        }
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();

    }


}
