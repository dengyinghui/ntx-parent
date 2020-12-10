package com.ntx.demo.controller;

import com.ntx.base.controller.BaseController;
import com.ntx.base.util.BaseResponse;
import com.ntx.base.util.ResponseUtil;
import com.ntx.demo.vo.TestBodyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public BaseResponse test(
            @RequestParam(value = "name") @NotBlank(message = "name不能为空") String name,
            @RequestParam(value = "age") @NotNull(message = "age不能为空") Integer age){
        boolean flag = DistributedLockUtil.tryLock("hello");
        if(flag){
            try{
                System.out.println("aaaaaaaaaaaaaaaaaaaaaa");
                Thread.sleep(3000);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return ResponseUtil.ok(this.redisTemplate.opsForValue().get("bhst:slove:ugc:String:trends:pageSize"));
    }

    @RequestMapping(value = "/testBody", method = RequestMethod.POST)
    public BaseResponse testBody(@Validated @RequestBody TestBodyVo testBodyVo){
        return ResponseUtil.ok(testBodyVo);
    }




}
