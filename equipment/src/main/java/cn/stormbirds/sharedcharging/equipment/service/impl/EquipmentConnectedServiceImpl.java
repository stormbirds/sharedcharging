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
public class EquipmentConnectedServiceImpl implements IMqttOnlineService {
    private final RedisUtil redisUtil;

    @Autowired
    public EquipmentConnectedServiceImpl(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public int onLine(String clientId) {
        log.info("接收到客户端:{} 连接消息",clientId);
        return (redisUtil.sSet("Equipment:Online",clientId)).intValue();

    }

    @Override
    public int offline(String clientId) {
        log.info("客户端:{} 断开了连接",clientId);
        return (redisUtil.setRemove("Equipment:Online", clientId)).intValue();

    }
}
