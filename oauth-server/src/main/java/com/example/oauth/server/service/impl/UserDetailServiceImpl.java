package com.example.oauth.server.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.example.oauth.server.entity.PlatformUserEntity;
import com.example.oauth.server.service.PlatformUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author slc
 */
@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private PlatformUserService platformUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String opeType = request.getHeader("ope_type");
        if (!StringUtils.isEmpty(opeType)) {
            log.info("操作人：{}", opeType);
            log.info("操作人：{}", opeType);
            log.info("操作人：{}", opeType);
            log.info("操作人：{}", opeType);
        }
        PlatformUserEntity user = platformUserService.findByUserName(username);
        if (user == null) {
            return null;
        }
        List<SimpleGrantedAuthority> list = Arrays.stream(user.getAuthorization().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new User(user.getUsername(), user.getPassword(),list );
    }
}
