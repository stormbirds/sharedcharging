package cn.stormbirds.sharedcharging.web.controller;

import cn.stormbirds.sharedcharging.common.base.BaseController;
import cn.stormbirds.sharedcharging.web.domain.ResultCode;
import cn.stormbirds.sharedcharging.web.domain.ResultJson;
import io.swagger.annotations.Api;
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
 * @since 2019/10/20 下午4:53
 */
@Api(tags = "订单控制器")
@RestController
@RequestMapping(value = "/app/v1/order")
public class OrderController  extends BaseController {

    @PostMapping(value = "/toRent")
    public ResultJson toRent(@RequestParam int eqCode){
        if(eqCode>0)
            return ResultJson.ok("接收到请求："+eqCode);
        else return ResultJson.failure(ResultCode.NOT_FOUND,"设备不存在或设备不在线");
    }
}
