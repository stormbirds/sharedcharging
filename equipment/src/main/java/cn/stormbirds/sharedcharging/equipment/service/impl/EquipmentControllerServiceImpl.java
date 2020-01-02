package cn.stormbirds.sharedcharging.equipment.service.impl;

import cn.stormbirds.sharedcharging.api.equipment.IEquipmentControllerService;
import cn.stormbirds.sharedcharging.equipment.mqtt.MqttSender;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;

/**
 * <p>
 * cn.stormbirds.sharedcharging.equipment.service.impl
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/10/20 下午11:16
 */
@Service(version = "${equipment.service.version}")
public class EquipmentControllerServiceImpl implements IEquipmentControllerService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MqttSender mqttSender;
    @Override
    public int ejectBatteryByDeviceId(String eqCode) {
        if(redisTemplate.opsForSet().isMember("Equipment:Online",eqCode)){
            mqttSender.sendToMqtt("Equipment:EqCode:"+eqCode,2,"释放电池");
            return 1;
        }
        return 0;
    }

    @Override
    public int updateVideoByPath(String eqCode, String videoFile) {
        return 0;
    }

    @Override
    public int updateQRcode(String eqCode, String qrUrl) {
        return 0;
    }

    @Override
    public int restartDevice(String eqCode) {
        return 0;
    }

    @Override
    public String getQrCode(String eqCode) {
        return "http://127.0.0.1:8081/api/v1/equipment/scanQrCodeH5/" + DigestUtils.md5DigestAsHex(eqCode.getBytes());
    }

}
