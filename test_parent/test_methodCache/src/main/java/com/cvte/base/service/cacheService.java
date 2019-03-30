package com.cvte.base.service;

import com.cvte.base.dao.UserDao;
import com.cvte.base.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class cacheService {

    @Autowired
    private UserDao userDao;


    /**
     * Spring Cache 使用方法与 Spring 对事务管理的配置相似。
     * Spring Cache 的核心就是对某个方法进行缓存，其实质就是缓存该方法的返回结果，
     * 并把方法参数和结果用键值对的方式存放到缓存中，当再次调用该方法使用相应的参数时，
     * 就会直接从缓存里面取出指定的结果进行返回。
     */


    /**
     * 方法添加缓存注解，这样当此方法第一次运行，在
     * 缓存中没有找到对应的 value 和 key，则将查询结果放入缓存。
     */
    @Cacheable(value = "user",key = "#id")
    public User findById(String id){

        User user = userDao.findById(id).get();
        return user;
    }


    /**
     * 当我们对数据进行删改的时候，需要更新缓存。其实更新缓存也就是清除缓存，因
     * 为清除缓存后，用户再次调用查询方法无法提取缓存会重新查找数据库中的记录并放入
     * 缓存
     */
    @CacheEvict(value = "user",key = "#user.id")
    public void update(User user){

        userDao.save(user);
    }

    @CacheEvict(value = "user",key = "#id")
    public void deleteById(String id){

        userDao.deleteById(id);
    }

}
