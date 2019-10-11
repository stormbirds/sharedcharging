package cn.stormbirds.sharedcharging.users.config;

import cn.stormbirds.sharedcharging.common.utils.IdCenter;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
@EnableTransactionManagement
@Configuration
public class UsersServiceRegisterConfig {

    @NacosValue(value = "${users.idcenter.datacenterId:0}")
    private long dataCenterId;

    @Bean
    public IdCenter idCenter(){
        return IdCenter.INSTENSE.init(dataCenterId,0,"Users service Id生成器服务");
    }
}
