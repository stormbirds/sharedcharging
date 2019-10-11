package cn.stormbirds.sharedcharging.web.config.security;

import cn.stormbirds.sharedcharging.api.users.ISpbUsersService;
import cn.stormbirds.sharedcharging.model.users.SpbUsers;
import cn.stormbirds.sharedcharging.web.domain.ResponseUserToken;
import cn.stormbirds.sharedcharging.web.domain.ResultCode;
import cn.stormbirds.sharedcharging.web.domain.ResultJson;
import cn.stormbirds.sharedcharging.web.domain.auth.AuthUserDetails;
import cn.stormbirds.sharedcharging.web.exception.CustomControllerException;
import cn.stormbirds.sharedcharging.web.utils.JWTUtils;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * cn.stormbirds.sharedcharging.web.config.security
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/10/10 18:25
 */

@Service
public class AuthServiceImpl {

    @Reference(version = "${users.service.version}")
    private ISpbUsersService usersService;

    @Resource(name = "defaultPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @NacosValue(value = "${application.name:SPB}", autoRefreshed = true)
    private String applicationName;

    public ResponseUserToken login(String username, String password){
        final Authentication authentication = jwtUtils.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthUserDetails userDetails = (AuthUserDetails) authentication.getPrincipal();
        final String token = jwtUtils.generateAccessToken(userDetails);
        ResponseUserToken responseUserToken = new ResponseUserToken();
        responseUserToken.setAccess_token(token);
        responseUserToken.setApplication(applicationName);
        responseUserToken.setExpires_in(jwtUtils.getExpirationFromToken(token));
        return responseUserToken;
    }

    public SpbUsers register(SpbUsers user) {
        if (usersService.findByUsername(user.getUsername()) != null) {
            throw new CustomControllerException(ResultJson.failure(ResultCode.BAD_REQUEST, "该用户名已经被注册"));
        }

        final String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setLastPasswordResetDate(LocalDateTime.now());
        user.setCreatedAt(LocalDateTime.now());
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        final SpbUsers userRegistered = usersService.register(user);
        if (userRegistered != null) {
            return userRegistered;
        }
        return null;

    }
}
