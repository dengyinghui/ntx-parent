package com.ntx.demo.controller;

import com.ntx.base.controller.BaseController;
import com.ntx.base.util.BaseResponse;
import com.ntx.base.util.ResponseUtil;
import com.ntx.common.util.SnowFlakeUtil;
import com.ntx.demo.service.DemoService;
import com.ntx.demo.vo.TestBodyVo;
import lombok.extern.slf4j.Slf4j;
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




}
