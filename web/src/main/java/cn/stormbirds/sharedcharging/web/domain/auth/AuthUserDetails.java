package cn.stormbirds.sharedcharging.web.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * <p>
 * cn.stormbirds.sharedcharging.web.domain.auth
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/9/20 16:11
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserDetails implements UserDetails {
    private Long id;

    private String username;

    private String password;

    private Boolean enabled;

    private Boolean accountNonLocked;

    private LocalDateTime lastPasswordResetDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
