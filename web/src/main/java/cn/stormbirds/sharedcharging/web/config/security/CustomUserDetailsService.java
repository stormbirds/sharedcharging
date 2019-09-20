package cn.stormbirds.sharedcharging.web.config.security;

import cn.stormbirds.sharedcharging.web.domain.auth.AuthUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


/**
 *
 * <p> 登陆身份认证
 * </p>
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/9/20 15:54
 *
 */
@Component(value="CustomUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
//    private final AuthMapper authMapper;
//
//    public CustomUserDetailsService(AuthMapper authMapper) {
//        this.authMapper = authMapper;
//    }

    @Override
    public AuthUserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
//        User user = authMapper.findByUsername(name);
//        if (user == null) {
//            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", name));
//        }
//        Role role = authMapper.findRoleByUserId(user.getId());
//        user.setRole(role);
        return null;
    }
}