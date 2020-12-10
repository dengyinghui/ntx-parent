package com.ntx.base.controller;

import com.ntx.base.util.BaseResponse;
import com.ntx.base.util.ResponseUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
public class BaseController {

    @RequestMapping(value = "/base/currentDate", method = RequestMethod.GET)
    public BaseResponse currentDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResponseUtil.ok(dateFormat.format(new Date()));
    }

    @RequestMapping(value = "/base/timestamp", method = RequestMethod.GET)
    public BaseResponse timestamp(){
        return ResponseUtil.ok(new Date().getTime());
    }



}
