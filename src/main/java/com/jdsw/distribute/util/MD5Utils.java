package com.jdsw.distribute.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.Random;

public class MD5Utils {
    /**
     * 加密登录密码
     * @param password 密码
     * @param rand 盐值
     * @return
     */
    public static String loginMd5(String password,String rand){
        String hashAlgorithmName = "md5";//加密方式
        ByteSource salt = ByteSource.Util.bytes(rand);
        int hashIterations = 2;//加密2次
        Object result = new SimpleHash(hashAlgorithmName,password,salt,hashIterations);
        return result.toString();
    }

    /**
     * 获取随机盐值
     * @param length 长度
     * @return
     */
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
