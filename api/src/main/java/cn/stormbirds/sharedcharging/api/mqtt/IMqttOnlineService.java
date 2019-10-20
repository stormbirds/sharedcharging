package cn.stormbirds.sharedcharging.api.mqtt;


/**
 * <p>
 * MQTT生产者消息发送接口
 * MessagingGateway要指定生产者的通道名称
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/10/17 上午1:22
 */

public interface IMqttOnlineService {

    void onLine(String clientId);
    void offline(String clientId);
}
