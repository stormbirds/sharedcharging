package cn.stormbirds.sharedcharging.web.config.security;

import cn.stormbirds.sharedcharging.api.users.ISpbUsersService;
import cn.stormbirds.sharedcharging.model.users.SpbRole;
import cn.stormbirds.sharedcharging.model.users.SpbUsers;
import cn.stormbirds.sharedcharging.web.domain.auth.AuthUserDetails;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


/**
 * <p> 登陆身份认证
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/9/20 15:54
 */
@Component(value = "CustomUserDetailsService")
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Reference(version = "${users.service.version}")
    private ISpbUsersService usersService;


    @Override
    public AuthUserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        SpbUsers user = usersService.findByUsername(name);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", name));
        }
        SpbRole role = usersService.findRoleByUserId(user.getId());
        return new AuthUserDetails(user, role);
    }
}