package cn.stormbirds.sharedcharging.equipment.config;

import cn.stormbirds.sharedcharging.common.utils.IdCenter;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@NacosPropertySource(dataId = "shared_power_bank_users_service-dev.properties", autoRefreshed = true)
@EnableDubbo
@EnableTransactionManagement
@Configuration
public class UsersServiceRegisterConfig {

    @NacosValue(value = "${users.idcenter.datacenterId:1}")
    private long dataCenterId;

    @Bean
    public IdCenter idCenter(){
        return IdCenter.INSTENSE.init(dataCenterId,0,"equipment service Id生成器服务");
    }
}