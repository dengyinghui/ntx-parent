package com.ntx.business.usercenter.service;

import com.ntx.business.usercenter.entity.MerchantsUser;
import com.ntx.business.usercenter.mapper.MerchantsUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantsUserService {

    @Autowired
    private MerchantsUserMapper merchantsUserMapper;

    public int saveMerchantsAccount(MerchantsUser merchantsUser){
        return this.merchantsUserMapper.saveMerchantsAccount(merchantsUser);
    }


}
