package cn.stormbirds.sharedcharging.users.service.impl;


import cn.stormbirds.sharedcharging.api.users.ISpbUsersService;
import cn.stormbirds.sharedcharging.common.utils.IdCenter;
import cn.stormbirds.sharedcharging.model.users.SpbUsers;
import cn.stormbirds.sharedcharging.users.mapper.SpbUsersMapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author stormbirds
 * @since 2019-09-20
 */
@Service(version = "${users.service.version}")
public class SpbUsersServiceImpl extends ServiceImpl<SpbUsersMapper, SpbUsers> implements ISpbUsersService {

    @Autowired
    private IdCenter idCenter;

    @Override
    public boolean login(String username, String password) {
        return false;
    }

    @Override
    public boolean findByUsername(String username) {
        return baseMapper.selectCount(Wrappers.<SpbUsers>lambdaQuery()
                .eq(SpbUsers::getUsername, username)) > 0;
    }

    @Override
    public SpbUsers register(String username, String password) {
        if (findByUsername(username)) {
            throw new IllegalArgumentException("该用户名已经被注册");
        }
        SpbUsers user = SpbUsers.builder().id(idCenter.getId())
                .accountNonLocked(true)
                .createdAt(LocalDateTime.now())
                .enabled(true)
                .lastPasswordResetDate(LocalDateTime.now())
                .password(password)
                .username(username)
                .build();
        if (super.save(user)) {
            return user;
        }
        return null;
    }
}
