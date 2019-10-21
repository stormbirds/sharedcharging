package cn.stormbirds.sharedcharging.web.utils;


import cn.stormbirds.sharedcharging.common.config.MqttConstants;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

/**
 * <p>
 * cn.stormbirds.sharedcharging.web.utils
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/10/17 下午11:17
 */
@Service
@MessagingGateway(defaultRequestChannel = MqttConstants.CHANNEL_NAME_OUT)
public interface MqttSender {
    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic,
                    @Header(MqttHeaders.QOS) int qos,
                    String payload);
}
