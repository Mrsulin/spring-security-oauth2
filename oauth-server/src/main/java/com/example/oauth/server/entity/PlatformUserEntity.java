package com.example.oauth.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * (PlatformUser)实体类
 *
 * @author slc
 * @since 2021-05-21 18:00:14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("platform_user")
public class PlatformUserEntity extends Model<PlatformUserEntity> implements Serializable {
    private static final long serialVersionUID = -23253090473501261L;
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String authorization;
    /**
     * 1：平台用户 2：系统用户
     */
    private Integer type;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
