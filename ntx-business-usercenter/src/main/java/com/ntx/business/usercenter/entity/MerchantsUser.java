package com.ntx.business.usercenter.entity;

import com.ntx.base.entity.BaseEntity;

import javax.validation.constraints.NotEmpty;

/**
 * 商铺用户
 */
public class MerchantsUser extends BaseEntity {

    //昵称
    private String nickName;
    //商户手机号码(商户登录帐号)
    @NotEmpty(message = "手机号码不能为空")
    private String telephone;
    //密码
    @NotEmpty(message = "密码不能为空")
    private String password;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
