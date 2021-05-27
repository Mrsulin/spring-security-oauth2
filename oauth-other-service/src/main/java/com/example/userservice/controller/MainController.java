package com.example.userservice.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author slc
 */
@RequestMapping("other")
@RestController
public class MainController {


    @GetMapping("method")
    public ResponseEntity<?>method(Principal principal){
        return ResponseEntity.status(200).body("成功进入other服务的方法,操作用户为:"+principal.getName());
    }
}
