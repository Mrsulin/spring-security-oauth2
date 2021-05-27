package com.example.oauth.server.auth;

import com.example.oauth.server.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author slc
 */
@Configuration
public class AuthorizationJwtConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 让oauth的token与jwt做关联
     *
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }



    /**
     * jwtToken转换器
     *
     * @return jwt rsa加密
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        //为转换器提供一个签名----------对称加密
        //jwtAccessTokenConverter.setSigningKey(CommonConstant.SIGN_KEY);
        //rsa加密
        ClassPathResource resource = new ClassPathResource("jwt.jks");
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource,"ku_9527".toCharArray());
        //如果keyPass和storePass密码不一致，则需要添加第二个参数 keyPass
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("rsa_for_jwt","key_9527".toCharArray());
        PrivateKey aPrivate = keyPair.getPrivate();
        PublicKey aPublic = keyPair.getPublic();

        jwtAccessTokenConverter.setKeyPair(keyPair);
        return jwtAccessTokenConverter;
    }

    /**
     * 配置第三方应用
     * <p>
     * 四种授权类型:
     * "authorization_code", "implicit", "password", "client_credentials", "refresh_token"
     * -1.Code码授权  authorization_code
     * -2.静默授权   implicit
     * -3.密码授权 （特别信任第三方应用） password
     * -4.客户端授权（直接通过浏览器获取token） client_credentials
     *
     * @param clients client
     * @throws Exception e
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("code-client")
                .secret(passwordEncoder.encode("code-client-123"))
                .scopes("read", "write")//客户端的业务作用域，自己定义的
                .authorizedGrantTypes("authorization_code")//四种授权类型
                .accessTokenValiditySeconds(7200)//token的有效时间
                .redirectUris("https://www.baidu.com")//授权后跳转的URI地址
                .and()
                .withClient("direct-client")
                .secret(passwordEncoder.encode("direct-client-123"))
                .scopes("read", "write")
                .authorizedGrantTypes("implicit") //静默模式
                .accessTokenValiditySeconds(3600)
                .redirectUris("https://www.baidu.com")
                .and()
                .withClient("password-client")
                .secret(passwordEncoder.encode("password-client-123"))
                .scopes("read", "write")
                .authorizedGrantTypes("password","refresh_token") //密码模式
                .accessTokenValiditySeconds(3600)
                .redirectUris("https://www.baidu.com")
                .and()
                .withClient("client")
                .secret(passwordEncoder.encode("client-123"))
                .authorizedGrantTypes("client_credentials") //客户端模式 不需要登录，也不需要授权，一般token时间比较短  访问最基本的接口（例如没有被权限注解管理的普通接口）
                .scopes("r--", "w--")
                .accessTokenValiditySeconds(3600)
                .redirectUris("https://www.baidu.com")//授权后跳转的URI地址

        ;
    }


    /**
     * 注入authenticationManager ，（在securityConfig中将该配置类注入）
     * 来支持 password grant type
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 暴露token授权服务给token的存储
     *
     * @param endpoints 断点
     * @throws Exception e
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                //还需要将转换器放到授权暴露端点中
                .accessTokenConverter(jwtAccessTokenConverter())
                //refresh_Token时需要userDetailService
                .userDetailsService(userDetailService)
                .reuseRefreshTokens(true);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients().checkTokenAccess("permitAll()");
    }
}
