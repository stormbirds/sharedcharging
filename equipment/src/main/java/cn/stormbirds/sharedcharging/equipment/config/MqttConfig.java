package cn.stormbirds.sharedcharging.equipment.config;


import cn.stormbirds.sharedcharging.api.mqtt.IMqttOnlineService;
import cn.stormbirds.sharedcharging.model.mqtt.ConnectedPayload;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import static cn.stormbirds.sharedcharging.common.config.MqttConstants.*;

/**
 * <p>
 * MQTT配置，生产者
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/10/17 上午12:59
 */
@Slf4j
@Configuration
public class MqttConfig {


    @NacosValue("${mqtt.username:admin}")
    private String username;

    @NacosValue("${mqtt.password:public}")
    private String password;

    @NacosValue("${mqtt.url:tcp://wifi.stormbirds.cn:1883}")
    private String url;

    @NacosValue("${mqtt.producer.clientId:producer}")
    private String producerClientId;

    @NacosValue("${mqtt.producer.defaultTopic:producerTopic}")
    private String producerDefaultTopic;

    @NacosValue("${mqtt.consumer.clientId:consumer}")
    private String consumerClientId;

    @NacosValue("${mqtt.consumer.defaultTopic:consumerTopic}")
    private String consumerDefaultTopic;


    @Autowired
    private IMqttOnlineService mqttOnlineService;

    /**
     * MQTT连接器选项
     *
     * @return {@link org.eclipse.paho.client.mqttv3.MqttConnectOptions}
     */
    @Bean
    public MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，
        // 这里设置为true表示每次连接到服务器都以新的身份连接
        options.setCleanSession(true);
        // 设置连接的用户名
//        options.setUserName(username);
        // 设置连接的密码
//        options.setPassword(password.toCharArray());
//        assert StringUtils.split(url, ",") != null;
        options.setServerURIs(/*StringUtils.split(url, ",")*/"tcp://wifi.stormbirds.cn:1883".split(","));
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(10);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送心跳判断客户端是否在线，但这个方法并没有重连的机制
        options.setKeepAliveInterval(20);
        // 设置“遗嘱”消息的话题，若客户端与服务器之间的连接意外中断，服务器将发布客户端的“遗嘱”消息。
        options.setWill("willTopic", WILL_DATA, 2, false);
        return options;
    }


    /**
     * MQTT客户端
     *
     * @return {@link org.springframework.integration.mqtt.core.MqttPahoClientFactory}
     */
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        return factory;
    }

    /**
     * MQTT信息通道（生产者）
     *
     * @return {@link org.springframework.messaging.MessageChannel}
     */
    @Bean(name = CHANNEL_NAME_OUT)
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    /**
     * MQTT消息处理器（生产者）
     *
     * @return {@link org.springframework.messaging.MessageHandler}
     */
    @Bean
    @ServiceActivator(inputChannel = CHANNEL_NAME_OUT)
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(
                producerClientId,
                mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(producerDefaultTopic);
        return messageHandler;
    }

    /**
     * MQTT消息订阅绑定（消费者）
     *
     * @return {@link org.springframework.integration.core.MessageProducer}
     */
    @Bean
    public MessageProducer inbound() {
        // 可以同时消费（订阅）多个Topic
//        assert StringUtils.split(consumerDefaultTopic, ",") != null;
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        consumerClientId, mqttClientFactory(),
                        "$SYS/brokers/+/clients/#".split(",") /*"$SYS/brokers/+/clients/+/connected","$SYS/brokers/+/clients/+/disconnected"*/);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        // 设置订阅通道
        adapter.setOutputChannel(mqttInboundChannel());
        return adapter;
    }

    /**
     * MQTT信息通道（消费者）
     *
     * @return {@link org.springframework.messaging.MessageChannel}
     */
    @Bean(name = CHANNEL_NAME_IN)
    public MessageChannel mqttInboundChannel() {
        return new DirectChannel();
    }

    @Bean(name = CHANNEL_SYNC_NAME_IN)
    public MessageChannel mqttInboundSyncChannel(){
        return new DirectChannel();
    }
    /**
     * MQTT消息处理器（消费者）
     *
     * @return {@link org.springframework.messaging.MessageHandler}
     */
    @Bean
    @ServiceActivator(inputChannel = CHANNEL_NAME_IN)
    public MessageHandler handler() {

        return message -> {
            Gson gson = new Gson();
            ConnectedPayload connectedPayload = gson.fromJson((String) message.getPayload(), ConnectedPayload.class);
            String receivedTopic = (String) message.getHeaders().get("mqtt_receivedTopic");
            if (receivedTopic != null &&
                    receivedTopic.startsWith("$SYS/brokers/") &&
                    receivedTopic.endsWith("/connected")) {
                if(mqttOnlineService.onLine(connectedPayload.getClientid())==1){
                    log.info("========={}=====Device Online======{}============", message.getHeaders(), message.getPayload());
                }
            } else if (receivedTopic != null &&
                    receivedTopic.startsWith("$SYS/brokers/") &&
                    receivedTopic.endsWith("/disconnected")) {
                if(mqttOnlineService.offline(connectedPayload.getClientid())==1){
                    log.info("========={}=====Device Offline======{}============", message.getHeaders(), message.getPayload());
                }
            }

        };
    }
}
