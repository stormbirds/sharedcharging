package cn.stormbirds.sharedcharging.equipment.service.impl;

import cn.stormbirds.sharedcharging.api.equipment.IEquipmentControllerService;
import cn.stormbirds.sharedcharging.equipment.domain.EquipmentInfo;
import cn.stormbirds.sharedcharging.equipment.domain.EquipmentOperations;
import cn.stormbirds.sharedcharging.equipment.domain.EquipmentOperationsMessage;
import cn.stormbirds.sharedcharging.equipment.mqtt.MqttSender;
import cn.stormbirds.sharedcharging.model.equipment.VideoFile;
import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Objects;

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
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private MqttSender mqttSender;

    @Override
    public int ejectBatteryByDeviceId(String eqCode) {

        if (redisTemplate.opsForSet().isMember("Equipment:Online", eqCode)) {
            EquipmentInfo equipmentInfo = JSON.parseObject(
                    Objects.requireNonNull(
                            redisTemplate.opsForHash().get("Equipment:EqInfo", eqCode))
                            .toString(),
                    EquipmentInfo.class);
            if (equipmentInfo == null || equipmentInfo.powerBanks.isEmpty()) {
                return 0;
            }
            mqttSender.sendToMqtt("Equipment:EqCode:" + eqCode, 2,
                    JSON.toJSONString(EquipmentOperationsMessage.builder()
                            .operations(EquipmentOperations.EJECT_BATTERY)
                            .EqCode(eqCode)
                            .payload(JSON.toJSONString(equipmentInfo.powerBanks.get(0)))
                            .build()
                    )
            );
            return 1;
        }
        return 0;
    }

    @Override
    public int updateVideoByPath(String eqCode, List<VideoFile> videoFiles) {
        if (redisTemplate.opsForSet().isMember("Equipment:Online", eqCode)) {
            EquipmentInfo equipmentInfo = JSON.parseObject(
                    Objects.requireNonNull(
                            redisTemplate.opsForHash().get("Equipment:EqInfo", eqCode))
                            .toString(),
                    EquipmentInfo.class);
            if (equipmentInfo == null || equipmentInfo.powerBanks.isEmpty()) {
                return 0;
            }
            mqttSender.sendToMqtt("Equipment:EqCode:" + eqCode, 2,
                    JSON.toJSONString(EquipmentOperationsMessage.builder()
                            .operations(EquipmentOperations.UPDATE_VIDEO)
                            .EqCode(eqCode)
                            .payload(JSON.toJSONString(videoFiles))
                            .build()
                    )
            );
            return 1;
        }
        return 0;
    }

    @Override
    public int updateQRcode(String eqCode, String qrUrl) {
        if (redisTemplate.opsForSet().isMember("Equipment:Online", eqCode)) {
            EquipmentInfo equipmentInfo = JSON.parseObject(
                    Objects.requireNonNull(
                            redisTemplate.opsForHash().get("Equipment:EqInfo", eqCode))
                            .toString(),
                    EquipmentInfo.class);
            if (equipmentInfo == null || equipmentInfo.powerBanks.isEmpty()) {
                return 0;
            }
            mqttSender.sendToMqtt("Equipment:EqCode:" + eqCode, 2,
                    JSON.toJSONString(EquipmentOperationsMessage.builder()
                            .operations(EquipmentOperations.UPDATE_QRCODE)
                            .EqCode(eqCode)
                            .payload(qrUrl)
                            .build()
                    )
            );
            return 1;
        }
        return 0;
    }

    @Override
    public int restartDevice(String eqCode) {
        return 0;
    }

    @Override
    public String getQrCode(String eqCode) {
        return "http://192.168.6.198:8081/api/v1/equipment/scanQrCodeH5/" + DigestUtils.md5DigestAsHex(eqCode.getBytes());
    }

}
