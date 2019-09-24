package cn.stormbirds.sharedcharging.users.service.impl;


import cn.stormbirds.sharedcharging.api.users.ISpbUserRoleService;
import cn.stormbirds.sharedcharging.model.users.SpbUserRole;
import cn.stormbirds.sharedcharging.users.mapper.SpbUserRoleMapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Service;

/**
 * <p>
 * 用户权限关系表 服务实现类
 * </p>
 *
 * @author stormbirds
 * @since 2019-09-20
 */
@Service(version = "${users.service.version}")
public class SpbUserRoleServiceImpl extends ServiceImpl<SpbUserRoleMapper, SpbUserRole> implements ISpbUserRoleService {

}
