package cn.stormbirds.sharedcharging.web.config.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * token认证过滤器
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/9/20 15:46
 */


@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.header}")
    private String token_header;

//    @Autowired
//    private JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String auth_token = request.getHeader(this.token_header);
        final String auth_token_start = "Bearer ";
        if (StringUtils.isNotEmpty(auth_token) && auth_token.startsWith(auth_token_start)) {
            auth_token = auth_token.substring(auth_token_start.length());
        } else {
            // 不按规范,不允许通过验证
            auth_token = null;
        }

//        String username = jwtUtils.getUsernameFromToken(auth_token);
//
//        logger.info(String.format("Checking authentication for user %s.", username));
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            User user = jwtUtils.getUserFromToken(auth_token);
//            if (jwtUtils.validateToken(auth_token, user)) {
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                logger.info(String.format("Authenticated user %s, setting security context", username));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        }
        chain.doFilter(request, response);
    }
}
