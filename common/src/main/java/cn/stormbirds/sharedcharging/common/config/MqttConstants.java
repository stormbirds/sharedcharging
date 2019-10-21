package cn.stormbirds.sharedcharging.common.config;

/**
 * <p>
 * cn.stormbirds.sharedcharging.common.config
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/10/21 17:42
 */


public class MqttConstants {

    /**
     * 订阅的bean名称
     */
    public static final String CHANNEL_NAME_IN = "mqttInboundChannel";
    /**
     * 发布的bean名称
     */
    public static final String CHANNEL_NAME_OUT = "mqttOutboundChannel";

    /**
     * 遗嘱消息主题
     */
    public static final byte[] WILL_DATA = "offline".getBytes();


}
