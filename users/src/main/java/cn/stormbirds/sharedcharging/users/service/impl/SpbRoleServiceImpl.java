package cn.stormbirds.sharedcharging.users.service.impl;


import cn.stormbirds.sharedcharging.api.users.ISpbRoleService;
import cn.stormbirds.sharedcharging.model.users.SpbRole;
import cn.stormbirds.sharedcharging.users.mapper.SpbRoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author stormbirds
 * @since 2019-09-20
 */
@Service(version = "${users.service.version}")
public class SpbRoleServiceImpl extends ServiceImpl<SpbRoleMapper, SpbRole> implements ISpbRoleService {

}
