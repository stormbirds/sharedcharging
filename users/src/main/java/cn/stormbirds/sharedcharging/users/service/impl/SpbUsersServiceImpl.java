package cn.stormbirds.sharedcharging.users.service.impl;


import cn.stormbirds.sharedcharging.api.users.ISpbUsersService;
import cn.stormbirds.sharedcharging.model.users.SpbUsers;
import cn.stormbirds.sharedcharging.users.mapper.SpbUsersMapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Service;

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

}
