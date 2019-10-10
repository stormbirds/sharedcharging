package cn.stormbirds.sharedcharging.web.config.security;

import cn.stormbirds.sharedcharging.api.users.ISpbUsersService;
import cn.stormbirds.sharedcharging.model.users.SpbUsers;
import cn.stormbirds.sharedcharging.web.domain.ResultCode;
import cn.stormbirds.sharedcharging.web.domain.ResultJson;
import cn.stormbirds.sharedcharging.web.exception.CustomControllerException;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    @Autowired
    private PasswordEncoder passwordEncoder;

    public SpbUsers registerByNamePassword(SpbUsers user){
        if(usersService.findByUsername(user.getUsername())!=null){
            throw new CustomControllerException(ResultJson.failure(ResultCode.BAD_REQUEST,"该用户名已经被注册"));
        }

        final String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setLastPasswordResetDate(LocalDateTime.now());
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }
}
