package cn.stormbirds.sharedcharging.web.controller;

import cn.stormbirds.sharedcharging.model.users.SpbUsers;
import cn.stormbirds.sharedcharging.web.config.security.AuthServiceImpl;
import cn.stormbirds.sharedcharging.web.domain.ResponseUserToken;
import cn.stormbirds.sharedcharging.web.domain.ResultCode;
import cn.stormbirds.sharedcharging.web.domain.ResultJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * cn.stormbirds.sharedcharging.web.controller
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/9/26 下午11:40
 */
@Api(tags = "Auth认证服务")
@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthController {

    private final AuthServiceImpl authService;

    @Autowired
    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }


    @ApiOperation(value = "登陆接口，获取Token")
    @PostMapping(value = "/login")
    public ResultJson loginByUsernameAndPassword(@RequestParam String username, @RequestParam String password) {
        final ResponseUserToken userToken = authService.login(username,password);
        if (userToken!=null) {
            return ResultJson.ok(userToken);
        } else {
            return ResultJson.failure(ResultCode.LOGIN_ERROR);
        }
    }

    @ApiOperation(value = "登陆接口，获取Token")
    @PostMapping(value = "/register")
    public ResultJson register(@RequestBody SpbUsers user) {
        final SpbUsers userRegistered = authService.register(user);
        if (userRegistered == null) {
            return ResultJson.failure(ResultCode.SERVER_ERROR);
        }
        return ResultJson.ok(userRegistered);
    }
}
