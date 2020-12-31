package com.jdsw.distribute.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Rand {
    public static String getTrackId(String string){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<2;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        StringBuffer st=new StringBuffer(string);
        SimpleDateFormat s = new SimpleDateFormat("yyMdHmss");
        String strd = s.format(new Date());
        return st.append(sb).append(strd).toString();
    }

    public static void main(String[] args) {
        System.out.println(getTrackId("WL"));
    }
}
