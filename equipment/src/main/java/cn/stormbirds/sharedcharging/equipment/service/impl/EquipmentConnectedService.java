package cn.stormbirds.sharedcharging.equipment.service.impl;

import cn.stormbirds.sharedcharging.api.mqtt.IMqttOnlineService;
import cn.stormbirds.sharedcharging.common.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * cn.stormbirds.sharedcharging.web
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/10/20 下午8:25
 */
@Slf4j
@Service
public class EquipmentConnectedService implements IMqttOnlineService {
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public void onLine(String clientId) {
        log.error("接收到客户端:{} 连接消息",clientId);
        redisUtil.sSet("Equipment:Online",clientId);
    }

    @Override
    public void offline(String clientId) {
        log.error("客户端:{} 断开了连接",clientId);
        redisUtil.setRemove("Equipment:Online",clientId);
    }
}
