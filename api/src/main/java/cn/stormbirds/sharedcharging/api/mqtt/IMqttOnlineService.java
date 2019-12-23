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

    /**
     * 设备上线操作
     * @param clientId 设备唯一识别码
     * @return 返回操作码 1操作成功、0操作失败
     */
    int onLine(String clientId);

    /**
     * 设备下线操作
     * @param clientId 设备唯一识别码
     * @return 返回操作码 1操作成功、0操作失败
     */
    int offline(String clientId);
}
