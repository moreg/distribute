package com.jdsw.distribute;

import com.jdsw.distribute.model.User;
import com.jdsw.distribute.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
public class RedisTest {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisUtil redisUtil;
    @Test
    public void insert(){
        redisUtil.set("LiYi","123");
    }
}
