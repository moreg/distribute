package com.jdsw.distribute.util;

import com.jdsw.distribute.dao.DevelopDao;
import com.jdsw.distribute.dao.NetworkDao;
import com.jdsw.distribute.dao.TelemarkeDao;
import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.service.NetworkService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Rand {
    public static String getTrackId(String string){
        String str="0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<4;i++){
            int number=random.nextInt(9);
            sb.append(str.charAt(number));
        }
        StringBuffer st=new StringBuffer(string);
        SimpleDateFormat s = new SimpleDateFormat("yyMMmmSSSS");
        String strd = s.format(new Date());
        String order = st.append(strd).append(sb).toString();
        return order;
    }

    public static void main(String[] args) {
        System.out.println(getTrackId("K"));
    }
}
