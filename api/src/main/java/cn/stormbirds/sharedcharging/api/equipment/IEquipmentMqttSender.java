package cn.stormbirds.sharedcharging.api.equipment;

/**
 * <p>
 * cn.stormbirds.sharedcharging.api.equipment
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/12/27 5:39 下午
 */
public interface IEquipmentMqttSender {
    void sendToMqtt(String topic, int qos, String payload);
}
