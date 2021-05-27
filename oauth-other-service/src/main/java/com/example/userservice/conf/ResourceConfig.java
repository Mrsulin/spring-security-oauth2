package com.example.userservice.conf;

import cn.hutool.Hutool;
import cn.hutool.core.io.FileUtil;
import com.example.oauth.CommonConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 解析jwt的token
 * 集成ResourceServerConfigurerAdapter
 *
 * @author slc
 */
@Configuration
public class ResourceConfig extends ResourceServerConfigurerAdapter {


    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 配置解析，也就是反转换
     *
     * @return JwtAccessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        //对称解密
        // jwtAccessTokenConverter.setSigningKey(CommonConstant.SIGN_KEY);

        //非对称解密

        ClassPathResource classPathResource = new ClassPathResource("public.txt");
        String publicKey = null;
        try {
            publicKey = FileUtil.readString(classPathResource.getFile(), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        jwtAccessTokenConverter.setVerifierKey(publicKey);
        return jwtAccessTokenConverter;
    }

    /**
     * 告诉资源服务器从哪里获取token，让资源服务器从tokenStore中获取token
     *
     * @param resources 资源
     * @throws Exception e
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

        resources.tokenStore(tokenStore());
    }
}
