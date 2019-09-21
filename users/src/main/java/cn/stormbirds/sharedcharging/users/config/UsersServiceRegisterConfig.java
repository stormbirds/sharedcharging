package cn.stormbirds.sharedcharging.users.config;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * cn.stormbirds.sharedcharging.users.config
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/9/21 14:40
 */
@NacosPropertySource(dataId = "shared_power_bank_users_service-dev.properties", autoRefreshed = true)
@Configuration
public class UsersServiceRegisterConfig {
//    @NacosInjected
//    private NamingService namingService;
//
//    @NacosValue("${server.port:8082}")
//    private int serverPort;
//
//    @NacosValue("${spring.application.name:shared_power_bank_users_service}")
//    private String applicationName;
//
//    @PostConstruct
//    public void registerInstance() throws NacosException {
//        namingService.registerInstance(applicationName,"127.0.0.1",serverPort);
//    }

}
