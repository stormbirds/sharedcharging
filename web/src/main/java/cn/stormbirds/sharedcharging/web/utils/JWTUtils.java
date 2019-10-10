package cn.stormbirds.sharedcharging.web.utils;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.stormbirds.sharedcharging.common.utils.DateTimeUtils;
import cn.stormbirds.sharedcharging.model.users.SpbUsers;
import cn.stormbirds.sharedcharging.web.domain.auth.AuthUserDetails;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * <p>
 * cn.stormbirds.sharedcharging.web.utils
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/9/20 16:02
 */

@Component
public class JWTUtils {

    public static final String ROLE_REFRESH_TOKEN = "ROLE_REFRESH_TOKEN";

    private static final String CLAIM_KEY_USER_ID = "user_id";
    private static final String CLAIM_KEY_AUTHORITIES = "scope";
    private static final String CLAIM_KEY_ACCOUNT_ENABLED = "enabled";
    private static final String CLAIM_KEY_ACCOUNT_NON_LOCKED = "non_locked";
    private static final String CLAIM_KEY_ACCOUNT_NON_EXPIRED = "non_expired";

    @NacosValue("${jwt.secret:123456}")
    private String secret;

    @NacosValue("${jwt.access_token.expiration:30000}")
    private Long access_token_expiration;

    @NacosValue("${jwt.refresh_token.expiration:30000}")
    private Long refresh_token_expiration;

    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    public AuthUserDetails getUserFromToken(String token) {
        AuthUserDetails userDetails;
        try {
            final Claims claims = getClaimsFromToken(token);
            Long userId = getUserIdFromToken(token);
            String username = claims.getSubject();
            List roles = (List) claims.get(CLAIM_KEY_AUTHORITIES);
            Collection<? extends GrantedAuthority> authorities = parseArrayToAuthorities(roles);
            boolean account_enabled = (Boolean) claims.get(CLAIM_KEY_ACCOUNT_ENABLED);
            boolean account_non_locked = (Boolean) claims.get(CLAIM_KEY_ACCOUNT_NON_LOCKED);
            boolean account_non_expired = (Boolean) claims.get(CLAIM_KEY_ACCOUNT_NON_EXPIRED);
            SpbUsers user = SpbUsers.builder()
                    .id(userId)
                    .username(username)
                    .accountNonLocked(account_non_locked)
                    .enabled(account_enabled)
                    .build();
            userDetails = new AuthUserDetails(userId, username, "password", account_enabled, account_non_expired, true, account_non_locked, authorities);
        } catch (Exception e) {
            userDetails = null;
        }
        return userDetails;
    }

    public AuthUserDetails getUserFromToken(Claims claims) {
        AuthUserDetails user;
        try {
            Long userId = Long.valueOf(claims.get(CLAIM_KEY_USER_ID).toString()) ;
            String username = claims.get(CLAIM_KEY_USER_ID).toString();
            List roles = (List) claims.get(CLAIM_KEY_AUTHORITIES);
            Collection<? extends GrantedAuthority> authorities = parseArrayToAuthorities(roles);
            boolean account_enabled = (Boolean) claims.get(CLAIM_KEY_ACCOUNT_ENABLED);
            boolean account_non_locked = (Boolean) claims.get(CLAIM_KEY_ACCOUNT_NON_LOCKED);
            boolean account_non_expired = (Boolean) claims.get(CLAIM_KEY_ACCOUNT_NON_EXPIRED);

            user = new AuthUserDetails(userId, username, "password", account_enabled, account_non_expired, true, account_non_locked, authorities);
        } catch (Exception e) {
            user = null;
        }
        return user;
    }

    public Long getUserIdFromToken(String token) {
        Long userId;
        try {
            final Claims claims = getClaimsFromToken(token);
            userId = Long.valueOf(claims.get(CLAIM_KEY_USER_ID).toString()) ;
        } catch (Exception e) {
            userId = -1L;
        }
        return userId;
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.get(CLAIM_KEY_USER_ID).toString();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = claims.getIssuedAt();
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 生成Token过期时间
     * @param expiration Token有效时间单位ms
     * @return
     */
    private Date generateExpirationDate(long expiration) {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * Token 是否过期
     *
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Token创建时间是否在修改密码之前
     * @param created Token创建时间
     * @param lastPasswordReset 上次修改密码时间
     * @return 是否在修改密码之前
     */
    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public String generateAccessToken(UserDetails userDetails) {
        AuthUserDetails user = (AuthUserDetails) userDetails;
        Map<String, Object> claims = generateClaims(user);
        claims.put(CLAIM_KEY_AUTHORITIES, JSON.toJSON(authoritiesToArray(user.getAuthorities())));
        return generateAccessToken(user.getUsername(), claims);
    }

    public Map<String, Object> generateAccessTokenToMap(UserDetails userDetails) {
        AuthUserDetails user = (AuthUserDetails) userDetails;
        Map<String, Object> claims = generateClaims(user);
        claims.put(CLAIM_KEY_AUTHORITIES, JSON.toJSON(authoritiesToArray(user.getAuthorities())));
        return claims;
    }

    private Map<String, Object> generateClaims(AuthUserDetails user) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(CLAIM_KEY_USER_ID, user.getId());
        claims.put(CLAIM_KEY_ACCOUNT_ENABLED, user.isEnabled());
        claims.put(CLAIM_KEY_ACCOUNT_NON_LOCKED, user.isAccountNonLocked());
        claims.put(CLAIM_KEY_ACCOUNT_NON_EXPIRED, user.isAccountNonExpired());
        return claims;
    }

    private String generateAccessToken(String subject, Map<String, Object> claims) {
        return generateToken(subject, claims, access_token_expiration);
    }

    private List authoritiesToArray(Collection<? extends GrantedAuthority> authorities) {
        List<String> list = new ArrayList<>();
        for (GrantedAuthority ga : authorities) {
            list.add(ga.getAuthority());
        }
        return list;
    }

    private Collection<? extends GrantedAuthority> parseArrayToAuthorities(List roles) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority authority;
        for (Object role : roles) {
            authority = new SimpleGrantedAuthority(role.toString());
            authorities.add(authority);
        }
        return authorities;
    }

    public String generateRefreshToken(UserDetails userDetails) {
        AuthUserDetails user = (AuthUserDetails) userDetails;
        Map<String, Object> claims = generateClaims(user);
        // 只授于更新 token 的权限
        String roles[] = new String[]{JWTUtils.ROLE_REFRESH_TOKEN};
        claims.put(CLAIM_KEY_AUTHORITIES, JSON.toJSON(roles));
        return generateRefreshToken(user.getUsername(), claims);
    }

    private String generateRefreshToken(String subject, Map<String, Object> claims) {
        return generateToken(subject, claims, refresh_token_expiration);
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token));
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            refreshedToken = generateAccessToken(claims.getSubject(), claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    private String generateToken(String subject, Map<String, Object> claims, long expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate(expiration))
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
    }

    public Boolean validateToken(String token, AuthUserDetails userDetails) {
        final Long userId = getUserIdFromToken(token);
        final String username = getUsernameFromToken(token);
         final Date created = getCreatedDateFromToken(token);
         final Date expiration = getExpirationDateFromToken(token);
        return (userId.equals(userDetails.getId())
                && username.equals(userDetails.getUsername())
                && !isTokenExpired(token)
                 && !isCreatedBeforeLastPasswordReset(created, DateTimeUtils.localDateTime2DateTime(userDetails.getLastPasswordResetDate()))
                && expiration.after(new Date())
        );
    }

}