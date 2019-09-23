package cn.stormbirds.sharedcharging.users.config;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <p>
 * cn.stormbirds.sharedcharging.users.config
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/9/21 14:40
 */
@Slf4j
@NacosPropertySource(dataId = "shared_power_bank_users_service-dev.properties", autoRefreshed = true)
@EnableDubbo(scanBasePackages = "cn.stormbirds.sharedcharging.users.service.impl")
@Configuration
public class UsersServiceRegisterConfig {
//    @NacosInjected
//    private NamingService namingService;
//
//    @NacosValue("${server.port:8082}")
//    private int serverPort;
//
//    @NacosValue("${spring.application.name:shared_power_bank}")
//    private String applicationName;
//
//    @NacosValue("${dubbo.service.address:127.0.0.1}")
//    private String serverAddress;
//
//    @PostConstruct
//    public void registerInstance() throws NacosException {
//        namingService.registerInstance(applicationName,serverAddress,serverPort);
//    }

}
