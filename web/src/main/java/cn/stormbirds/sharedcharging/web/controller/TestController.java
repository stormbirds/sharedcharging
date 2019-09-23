package cn.stormbirds.sharedcharging.web.controller;

import cn.stormbirds.sharedcharging.api.users.ISpbRoleService;
import cn.stormbirds.sharedcharging.common.base.BaseController;
import cn.stormbirds.sharedcharging.web.domain.ResultJson;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * cn.stormbirds.sharedcharging.web.controller
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/9/17 15:25
 */
@RestController
@RequestMapping(value = "/api/v1/test")
public class TestController extends BaseController {

    @NacosValue(value = "${test.name:demo}", autoRefreshed = true)
    private String userName;

    @Reference
    private ISpbRoleService roleService;

    @GetMapping(value = "/{id}")
    public ResultJson getTestOne(@PathVariable String id) {
        return ResultJson.ok(userName + id);
    }

    @GetMapping(value = "/roles")
    public ResultJson getRoleList(){
        return ResultJson.ok(roleService);
    }
}
