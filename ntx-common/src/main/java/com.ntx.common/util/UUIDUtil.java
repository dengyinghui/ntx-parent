package com.ntx.common.util;

import java.util.UUID;

public class UUIDUtil {

    public static void main(String[] args) {
        System.out.println(uuid());
    }

    /**
     * 获取uuid字符串
     * @return uuid字符串
     */
    public static String uuid(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

}
