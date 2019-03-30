package com.cvte.base;

import com.cvte.base.dao.UserDao;
import com.cvte.base.pojo.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void test(){

        List<User> users = userDao.findAll();

        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void test2() throws Exception {

        String allUser = redisTemplate.boundValueOps("allUser").get();
        if (allUser==null){
            System.out.println("从数据库获取数据");
            List<User> users = userDao.findAll();

            ObjectMapper om = new ObjectMapper();
            String json = om.writeValueAsString(users);

            redisTemplate.boundValueOps("allUser").set(json);
            System.out.println("数据存入Redis");
        }else {
            System.out.println("从Redis中获取数据");
        }

        System.out.println(allUser);
    }




}
