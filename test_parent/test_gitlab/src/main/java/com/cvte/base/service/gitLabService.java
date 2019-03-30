package com.cvte.base.service;

import com.cvte.base.dao.UserDao;
import com.cvte.base.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class gitLabService {

    @Autowired
    private UserDao userdao;
    
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    //查询所有
    public List<User> findAll(){

        List<User> userList = (List<User>)redisTemplate.opsForValue().get("userList");
        if (userList==null){
            userList = userdao.findAll();
            redisTemplate.opsForValue().set("userList",userList);
        }
        return userList;
    }

    //根据id查询
    public User findById(String id) {
        return userdao.findById(id).get();
    }

    //增加
    public void add(User user){

        //缓存同步
        redisTemplate.delete("user"+user.getId());

        user.setId("random_num");
        userdao.save(user);
    }

    //修改
    public void update(User user){

        //缓存同步
        redisTemplate.delete("user"+user.getId());

        userdao.save(user);
    }

    //根据id删除
    public void deleteById(String id){

        //缓存同步
        redisTemplate.delete("user"+id);

        userdao.deleteById(id);
    }


    //条件查询
    public List<User> findSearch(Map searchMap){

        List<User> userList=(List<User>)redisTemplate.opsForValue().get("userConditions");

        if (userList == null) {

            Specification<User> specification=createSpecification(searchMap);

            userList = userdao.findAll(specification);

            //数据存入Redis并设置过期时间
            redisTemplate.opsForValue().set("userCondition",userList,30, TimeUnit.SECONDS);
        }

        return userList;
    }


    //条件+分页查询
    public Page<User> findSearch(Map searchMap, int page, int size){

        Page<User> users=(Page<User>)redisTemplate.opsForValue().get("PageUsers");

        if (users==null){

            Specification<User> specification = createSpecification(searchMap);

            PageRequest pageRequest = PageRequest.of(page - 1, size);

            users = userdao.findAll(specification, pageRequest);

            redisTemplate.opsForValue().set("PageUsers",users);
        }

        return users;
    }


    //创建查询条件
    private Specification<User> createSpecification(Map searchMap) {

        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates=new ArrayList<>();

                if (searchMap.get("username")!=null
                        && !searchMap.get("username").equals("")){

                    predicates.add(criteriaBuilder.like(root.get("username").as(String.class),
                            "%" + searchMap.get("username") + "%"));
                }

                if (searchMap.get("name")!=null
                        && !searchMap.get("name").equals("")){

                    predicates.add(criteriaBuilder.like(root.get("name").as(String.class),
                            "%" + searchMap.get("name") + "%"));
                }


                Predicate[] predicatesArray=new Predicate[predicates.size()];
                return criteriaBuilder.and(predicates.toArray(predicatesArray));
            }
        };
    }

    //测试自定义SQL查询
    public void selfQuery(){

        List<User> users = userdao.selfQuery();
    }


    //测试自定义SQL修改
    @Transactional
    public void selfUpdate(){

        userdao.selfUpdate();
    }
}
