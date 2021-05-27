package com.example.oauth.server.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.oauth.server.entity.PlatformUserEntity;
import com.example.oauth.server.service.PlatformUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Api(tags = "业务接口")
@RestController
@RequestMapping("main")
public class MainController {

    @Autowired
    private PlatformUserService platformUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation("用户业务接口1-sys:user")
    @GetMapping("list")
    @PreAuthorize("hasAuthority('sys:user')")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(platformUserService.list());
    }


    @ApiOperation("用户业务接口-sys:ope")
    @PostMapping("add")
    @PreAuthorize("hasAuthority('sys:ope')")
    public ResponseEntity<?> add(@RequestBody PlatformUserEntity platformUserEntity) {
        platformUserEntity.setPassword(passwordEncoder.encode(platformUserEntity.getPassword()));
        platformUserEntity.insert();
        return ResponseEntity.ok("success");
    }

    @ApiOperation("用户业务接口-()")
    @GetMapping("limit")
    public ResponseEntity<?> get(Principal principal,int page, int limit) {
        return ResponseEntity.ok(platformUserService.page(new Page<>(page, limit)));
    }
}
