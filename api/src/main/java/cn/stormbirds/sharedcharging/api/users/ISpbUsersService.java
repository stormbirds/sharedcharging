package cn.stormbirds.sharedcharging.api.users;


import cn.stormbirds.sharedcharging.model.users.SpbRole;
import cn.stormbirds.sharedcharging.model.users.SpbUsers;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.context.annotation.Role;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author stormbirds
 * @since 2019-09-20
 */
public interface ISpbUsersService extends IService<SpbUsers> {
    boolean login(String username, String password);
    SpbUsers findByUsername(String username);
    SpbUsers register(SpbUsers user);
    SpbRole findRoleByUserId(Long userId);
}
