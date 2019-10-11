package cn.stormbirds.sharedcharging.users.service.impl;


import cn.stormbirds.sharedcharging.api.users.ISpbRoleService;
import cn.stormbirds.sharedcharging.api.users.ISpbUserRoleService;
import cn.stormbirds.sharedcharging.api.users.ISpbUsersService;
import cn.stormbirds.sharedcharging.common.utils.IdCenter;
import cn.stormbirds.sharedcharging.model.users.RoleNames;
import cn.stormbirds.sharedcharging.model.users.SpbRole;
import cn.stormbirds.sharedcharging.model.users.SpbUserRole;
import cn.stormbirds.sharedcharging.model.users.SpbUsers;
import cn.stormbirds.sharedcharging.users.mapper.SpbUsersMapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;

import static cn.stormbirds.sharedcharging.model.users.RoleNames.ROLE_USER;

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

    @Autowired
    private ISpbRoleService roleService;

    @Autowired
    private ISpbUserRoleService userRoleService;

    @Override
    public boolean login(String username, String password) {

        return false;
    }

    @Override
    public SpbUsers findByUsername(String username) {
        return baseMapper.selectOne(Wrappers.<SpbUsers>lambdaQuery()
                .eq(SpbUsers::getUsername, username));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public SpbUsers register(SpbUsers user) {
        if(this.save(user, ROLE_USER)){
            return user;
        }
        return null;
    }


    @Override
    public SpbRole findRoleByUserId(Long userId) {
        SpbUserRole userRole = userRoleService.getOne(Wrappers.<SpbUserRole>lambdaQuery()
                .eq(SpbUserRole::getUserId, userId));
        if (userRole != null) {
            return roleService.getById(userRole.getRoleId());
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean save(SpbUsers user, RoleNames roleNames) {
        user.setId(idCenter.getId());
        SpbRole role = roleService.getOne(Wrappers.<SpbRole>lambdaQuery().eq(SpbRole::getName, roleNames.name()));
        if (role == null) {
            log.error(String.format("Not Found SpbRole of %s for %s when save UserRole. " , roleNames.name() ,user.toString()) );
            return false;
        }
        SpbUserRole userRole = new SpbUserRole();
        userRole.setId(idCenter.getId());
        userRole.setRoleId(role.getId());
        userRole.setUserId(user.getId());
        if (userRoleService.save(userRole)) {
            if (super.save(user)) {
                return true;
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                log.error("User save failure to database, " + user.toString());
                return false;
            }
        }
        log.error("SpbUserRole save failure to database, " + userRole.toString());
        return false;
    }
}
