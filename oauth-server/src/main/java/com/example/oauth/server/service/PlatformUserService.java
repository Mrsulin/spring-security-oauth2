package com.example.oauth.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.oauth.server.entity.PlatformUserEntity;

/**
 * (PlatformUser)表服务接口
 *
 * @author slc
 * @since 2021-05-21 18:00:16
 */
public interface PlatformUserService extends IService<PlatformUserEntity> {

    /**
     * qq
     *
     * @param userName @
     * @return user
     */
    PlatformUserEntity findByUserName(String userName);

    /**
     * qqw
     *
     * @param userName @
     * @param type     @
     * @return user
     */
    PlatformUserEntity findByUserNameAndType(String userName, Integer type);
}
