package cn.stormbirds.sharedcharging.web.controller;

import cn.stormbirds.sharedcharging.api.equipment.IEquipmentMqttSender;
import cn.stormbirds.sharedcharging.api.users.ISpbRoleService;
import cn.stormbirds.sharedcharging.common.base.BaseController;
import cn.stormbirds.sharedcharging.common.utils.RedisUtil;
import cn.stormbirds.sharedcharging.model.users.SpbRole;
import cn.stormbirds.sharedcharging.web.domain.ResultJson;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import io.swagger.annotations.Api;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * cn.stormbirds.sharedcharging.web.controller
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/9/17 15:25
 */
@Api(tags = "测试控制器")
@RestController
@RequestMapping(value = "/api/v1/test")
public class TestController extends BaseController {

    @Autowired
    private RedisUtil redisUtil;

    @NacosValue(value = "${test.name:tcp://wifi.stormbirds.cn:1883}", autoRefreshed = true)
    private String userName;

    @Reference(version = "${users.service.version}")
    private ISpbRoleService roleService;

    @Reference(version = "${equipment.service.version}")
    private IEquipmentMqttSender mqttSenderService;

    /**
     * 拥有ROLE_ADMIN或者ROLE_USER权限的用户才有权限调用此方法
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResultJson getTestOne(@PathVariable String id) {
        return ResultJson.ok(userName + id);
    }

    @PreAuthorize("principal.username.equals(#username)")
    @GetMapping(value = "/myself")
    public ResultJson getMySelf(@RequestParam String userName){
        return ResultJson.ok();
    }

    /**
     * 拥有ROLE_ADMIN权限的用户才有权利调用此方法
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/roles")
    public ResultJson<List<SpbRole>> getRoleList(){
        return ResultJson.ok(roleService.list());
    }

    @GetMapping(value = "/redis")
    public ResultJson testRedis(){
        redisUtil.set("test","test_value",6000L);
        return ResultJson.ok(redisUtil.get("test"));
    }

    @PostMapping(value = "/mqttsend")
    public ResultJson<String> mqttSendTest(@RequestParam String topic,@RequestParam int qos){
        mqttSenderService.sendToMqtt(topic,qos,"{ \"msg\": \"Hello, World Test!\" }");
        return ResultJson.ok();
    }
}
