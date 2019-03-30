package com.cvte.base.Controller;

import com.cvte.base.pojo.User;
import com.cvte.base.service.gitLabService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gitlab")
@CrossOrigin
public class gitLabController {

    @Autowired
    private gitLabService gitLabService;

    @RequestMapping
    public Result findAll(){
        return new Result(true, StatusCode.OK,
                        "查询成功", gitLabService.findAll());
    }

    @GetMapping(value = "/{id}")
    public Result findById(@PathVariable String id){
        return new Result(true, StatusCode.OK,
                "查询成功", gitLabService.findById(id));
    }

    //增加
    @PostMapping
    public Result add(@RequestBody User user){
        gitLabService.add(user);
        return new Result(true,StatusCode.OK,"新增成功");

    }

    //修改
    @PutMapping
    public Result update(@RequestBody User user){
        gitLabService.update(user);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    //根据id删除
    @DeleteMapping(value = "/{id}")
    public Result deleteById(@PathVariable String id){
        gitLabService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }


    //条件查询
    @PostMapping(value = "/search")
    public Result findSearch(@RequestBody Map searchMap){

        List<User> userList = gitLabService.findSearch(searchMap);
        return new Result(true,StatusCode.OK,"查询成功",userList);
    }

    //分页+条件查询
    @PostMapping(value = "/search/{page}/{size}")
    public Result findSearch(@RequestBody Map searchMap,
                             @PathVariable int page,
                             @PathVariable int size){

        Page<User> pageList = gitLabService.findSearch(searchMap, page, size);

        return new Result(true, StatusCode.OK, "查询成功",
                new PageResult<User>(pageList.getTotalElements(),pageList.getContent()));
    }

    @RequestMapping("/selfQuery")
    public void test(){
        gitLabService.selfQuery();
    }

    @RequestMapping("/selfUpdate")
    public void selfUpdate(){
        gitLabService.selfUpdate();
    }
}
