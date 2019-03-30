package com.cvte.base.controller;

import com.cvte.base.pojo.User;
import com.cvte.base.service.cacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class cacheController {

    @Autowired
    private cacheService cacheService;


    @GetMapping("/{id}")
    public User findById(@PathVariable String id){

        return cacheService.findById(id);
    }


    @PostMapping
    public void update(@RequestBody User user){

        cacheService.update(user);
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id){

         cacheService.deleteById(id);
    }
}
