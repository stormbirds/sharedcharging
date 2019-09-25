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
@EnableDubbo
@Configuration
public class UsersServiceRegisterConfig {

}
