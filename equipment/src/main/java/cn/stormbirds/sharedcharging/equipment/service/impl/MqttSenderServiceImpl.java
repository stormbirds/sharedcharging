package cn.stormbirds.sharedcharging.equipment.service.impl;

import cn.stormbirds.sharedcharging.api.equipment.IEquipmentMqttSender;
import cn.stormbirds.sharedcharging.equipment.mqtt.MqttSender;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * cn.stormbirds.sharedcharging.equipment.service.impl
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/12/27 5:52 下午
 */
@Service(version = "${equipment.service.version}")
public class MqttSenderServiceImpl implements IEquipmentMqttSender {
    @Autowired
    MqttSender mqttSender;

    @Override
    public void sendToMqtt(String topic, int qos, String payload) {
        mqttSender.sendToMqtt(topic,qos,payload);
    }
}
