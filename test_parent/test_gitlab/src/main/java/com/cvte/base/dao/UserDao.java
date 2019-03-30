package com.cvte.base.dao;

import com.cvte.base.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserDao extends
        JpaRepository<User,String>, JpaSpecificationExecutor<User> {

    /**
     *  JpaRepository 提供了基本的增删改查
     *  JpaSpecificationExecutor 用于做复杂的条件
     */

    //测试自定义SQL查询
    @Query(value = "select u from User u")
    List<User> selfQuery();


    //测试自定义SQL修改
    @Modifying
    @Query("update User set username='zhangsan1'")
    void selfUpdate();


}
