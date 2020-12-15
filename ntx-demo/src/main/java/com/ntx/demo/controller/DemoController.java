package com.ntx.demo.controller;

import com.ntx.base.controller.BaseController;
import com.ntx.base.util.BaseResponse;
import com.ntx.base.util.ResponseUtil;
import com.ntx.common.util.ExceptionUtil;
import com.ntx.common.util.SnowFlakeUtil;
import com.ntx.demo.constant.RocketMQKey;
import com.ntx.demo.service.DemoService;
import com.ntx.demo.vo.TestBodyVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 示例Controller
 */
@RestController
@RequestMapping(value = "/demo")
@Validated
@Slf4j
public class DemoController extends BaseController {

    @Autowired
    private DemoService demoService;
    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public BaseResponse test(
            @RequestParam(value = "name") @NotBlank(message = "name不能为空") String name,
            @RequestParam(value = "age") @NotNull(message = "age不能为空") Integer age){
        TestBodyVo testBodyVo = new TestBodyVo();
        testBodyVo.setName(name);
        testBodyVo.setAge(age);
        testBodyVo.setId(SnowFlakeUtil.getId());
        int result = demoService.save(testBodyVo);
        return ResponseUtil.ok(result);
    }

    @RequestMapping(value = "/testBody", method = RequestMethod.POST)
    public BaseResponse testBody(@Validated @RequestBody TestBodyVo testBodyVo){
        return ResponseUtil.ok(testBodyVo);
    }


    @RequestMapping(value = "/testSendMQMessage", method = RequestMethod.GET)
    public BaseResponse testSendMQMessage(){
        for(int i = 0;i < 5;i++){
            Message message = new Message(RocketMQKey.TOPIC, RocketMQKey.TAG, "aaa", ("a" + i).getBytes());
            try{
                defaultMQProducer.send(message, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        System.out.println("发送成功.");
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        System.out.println("发送失败.");
                    }
                });
            } catch (Exception e){
                e.printStackTrace();
                log.info("发送消息到MQ发送异常:" + ExceptionUtil.getExceptionDetail(e));
            }

        }
        return ResponseUtil.ok(true);
    }



}
