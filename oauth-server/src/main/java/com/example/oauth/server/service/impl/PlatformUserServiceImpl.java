package com.example.oauth.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.oauth.server.dao.PlatformUserDao;
import com.example.oauth.server.entity.PlatformUserEntity;
import com.example.oauth.server.service.PlatformUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (PlatformUser)表服务实现类
 *
 * @author slc
 * @since 2021-05-21 18:00:17
 */
@Service("platformUserService")
public class PlatformUserServiceImpl extends ServiceImpl<PlatformUserDao, PlatformUserEntity> implements PlatformUserService {
    @Resource
    private PlatformUserDao platformUserDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public PlatformUserEntity findByUserName(String userName) {
        return this.getOne(new LambdaQueryWrapper<PlatformUserEntity>().eq(PlatformUserEntity::getUsername,userName));
    }

    @Override
    public PlatformUserEntity findByUserNameAndType(String userName, Integer type) {
        return this.getOne(new LambdaQueryWrapper<PlatformUserEntity>().eq(PlatformUserEntity::getUsername,userName).eq(PlatformUserEntity::getType,type));
    }
}
