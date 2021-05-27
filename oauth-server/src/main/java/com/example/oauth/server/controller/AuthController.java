package com.example.oauth.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("oauth")
public class AuthController {


    @GetMapping("getUserInfo")
    public Principal getUserInfo(Principal principal){
        return principal;
    }
}
