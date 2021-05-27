package com.example.oauth.server.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.oauth.server.entity.PlatformUserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * (PlatformUser)表数据库访问层
 *
 * @author slc
 * @since 2021-05-21 18:00:16
 */
@Mapper
public interface PlatformUserDao extends BaseMapper<PlatformUserEntity> {


}
