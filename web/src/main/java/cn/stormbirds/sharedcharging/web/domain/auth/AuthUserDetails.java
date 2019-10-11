package cn.stormbirds.sharedcharging.web.domain.auth;

import cn.stormbirds.sharedcharging.model.users.SpbRole;
import cn.stormbirds.sharedcharging.model.users.SpbUsers;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
@AllArgsConstructor
public class AuthUserDetails implements UserDetails {
    private Long id;

    private String username;

    private String password;

    private Boolean enabled;

    private Boolean accountNonLocked;

    /**
     * 用户角色权限
     */
    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * 账号是否过期
     */
    private final Boolean isAccountNonExpired;

    /**
     * 密码是否过期
     */
    private final Boolean isCredentialsNonExpired;

    private LocalDateTime lastPasswordResetDate;

    public AuthUserDetails(Long userId, String username, String password, boolean account_enabled, boolean account_non_expired,
                           boolean isCredentialsNonExpired, boolean account_non_locked, Collection<? extends GrantedAuthority> authorities,
                           LocalDateTime lastPasswordResetDate) {
        if (username != null && !"".equals(username) && password != null) {
            this.id = userId;
            this.username = username;
            this.password = password;
            this.enabled = account_enabled;
            this.isAccountNonExpired = account_non_expired;
            this.accountNonLocked = account_non_locked;
            this.isCredentialsNonExpired = isCredentialsNonExpired;
            this.authorities = authorities;
            this.lastPasswordResetDate = lastPasswordResetDate;
        } else {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }
    }

    public AuthUserDetails(SpbUsers user, SpbRole role) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.enabled = user.getEnabled();
        this.isAccountNonExpired = true;
        this.accountNonLocked = user.getAccountNonLocked();
        this.isCredentialsNonExpired = true;
        this.authorities = role != null ? new ArrayList<GrantedAuthority>() {{
            add(new SimpleGrantedAuthority(role.getName()));
        }} : new ArrayList<>();
        this.lastPasswordResetDate = user.getLastPasswordResetDate();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
