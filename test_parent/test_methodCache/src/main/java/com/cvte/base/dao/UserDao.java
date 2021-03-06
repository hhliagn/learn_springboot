package com.cvte.base.dao;

import com.cvte.base.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDao extends
        JpaRepository<User,String>, JpaSpecificationExecutor<User> {

}
