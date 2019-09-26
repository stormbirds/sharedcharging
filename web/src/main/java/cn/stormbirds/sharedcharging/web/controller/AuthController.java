package cn.stormbirds.sharedcharging.web.controller;

import cn.stormbirds.sharedcharging.api.users.ISpbUsersService;
import cn.stormbirds.sharedcharging.model.users.SpbUsers;
import cn.stormbirds.sharedcharging.web.domain.ResultCode;
import cn.stormbirds.sharedcharging.web.domain.ResultJson;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * cn.stormbirds.sharedcharging.web.controller
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/9/26 下午11:40
 */
@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthController {

    @Reference(version = "${users.service.version}")
    private ISpbUsersService usersService;

    @PostMapping(value = "/login")
    public ResultJson loginByUsernameAndPassword(@RequestParam String username, @RequestParam String password) {
        if (usersService.login(username, password)) {
            return ResultJson.ok();
        } else {
            return ResultJson.failure(ResultCode.LOGIN_ERROR);
        }
    }

    @PostMapping(value = "/register")
    public ResultJson register(@RequestParam String username, @RequestParam String password) {

        SpbUsers user = usersService.register(username, password);
        if (user == null) {
            return ResultJson.failure(ResultCode.SERVER_ERROR);
        }
        return ResultJson.ok(user);
    }
}
