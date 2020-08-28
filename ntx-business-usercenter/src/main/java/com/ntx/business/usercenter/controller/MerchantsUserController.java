package com.ntx.business.usercenter.controller;

import com.ntx.base.controller.BaseController;
import com.ntx.base.util.ResponseUtil;
import com.ntx.business.usercenter.entity.MerchantsUser;
import com.ntx.business.usercenter.service.MerchantsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商户帐号
 */
@RestController
@RequestMapping(value = "/merchantsAccount")
@Validated
public class MerchantsUserController extends BaseController {

    @Autowired
    private MerchantsUserService merchantsUserService;

    @RequestMapping(value = "/saveMerchantsAccount", method = RequestMethod.POST)
    public ResponseUtil saveMerchantsAccount(@RequestBody @Validated MerchantsUser merchantsAccount){
        return ResponseUtil.ok(this.merchantsUserService.saveMerchantsAccount(merchantsAccount));
    }


}
