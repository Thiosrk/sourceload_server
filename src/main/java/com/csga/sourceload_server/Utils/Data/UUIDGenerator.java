package com.csga.sourceload_server.Utils.Data;

import java.util.UUID;

public class UUIDGenerator {
    /**
     * 获取32位UUID字符串
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获取32位UUID大写字符串
     * @return
     */
    public static String getUpperCaseUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    /**
     * 获取指定数量的UUID字符串
     * @return
     */
    public static String[] getUUID(int number){
        if(number < 1){
            return null;
        }
        String[] ss = new String[number];
        for(int i=0;i<number;i++){
            ss[i] = getUUID();
        }
        return ss;
    }

}
