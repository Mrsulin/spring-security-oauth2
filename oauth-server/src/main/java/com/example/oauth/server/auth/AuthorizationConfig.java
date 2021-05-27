//package com.example.oauth.server.auth;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
//
///**
// * @author slc
// */
//@Configuration
//public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    /**
//     * redis 的链接工厂
//     */
//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;
//
//    /**
//     * 让Redis和oauth的token做关联
//     * @return redisToken
//     */
//    @Bean
//    public TokenStore tokenStore(){
//        return new RedisTokenStore(redisConnectionFactory);
//    }
//
//    /**
//     * 配置第三方应用
//     *
//     * 四种授权类型:
//     * "authorization_code", "implicit", "password", "client_credentials", "refresh_token"
//     *      -1.Code码授权  authorization_code
//     *      -2.静默授权   implicit
//     *      -3.密码授权 （特别信任第三方应用） password
//     *      -4.客户端授权（直接通过浏览器获取token） client_credentials
//     * @param clients client
//     * @throws Exception e
//     */
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory().withClient("code-client")
//                .secret(passwordEncoder.encode("code-client-123"))
//                .scopes("read","write")//客户端的业务作用域，自己定义的
//                .authorizedGrantTypes("authorization_code")//四种授权类型
//                .accessTokenValiditySeconds(7200)//token的有效时间
//                .redirectUris("https://www.baidu.com")//授权后跳转的URI地址
//                .and()
//                .withClient("direct-client")
//                .secret(passwordEncoder.encode("direct-client-123"))
//                .scopes("read","write")
//                .authorizedGrantTypes("implicit") //静默模式
//                .accessTokenValiditySeconds(3600)
//                .redirectUris("https://www.baidu.com")
//                .and()
//                .withClient("password-client")
//                .secret(passwordEncoder.encode("password-client-123"))
//                .scopes("read","write")
//                .authorizedGrantTypes("password") //密码模式
//                .accessTokenValiditySeconds(3600)
//                .redirectUris("https://www.baidu.com")
//                .and()
//                .withClient("client")
//                .secret(passwordEncoder.encode("client-123"))
//                .authorizedGrantTypes("client_credentials") //客户端模式 不需要登录，也不需要授权，一般token时间比较短  访问最基本的接口（例如没有被权限注解管理的普通接口）
//                .scopes("r--","w--")
//                .accessTokenValiditySeconds(3600)
//                .redirectUris("https://www.baidu.com")//授权后跳转的URI地址
//        ;
//    }
//
//
//
//    /**
//     * 注入authenticationManager ，（在securityConfig中将该配置类注入）
//     * 来支持 password grant type
//     */
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    /**
//     * 暴露token授权服务给token的存储
//     *
//     * 密码模式下，还需要将authenticationManager教官给端点
//     * @param endpoints 断点
//     * @throws Exception e
//     */
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager);
//    }
//}
