package cn.stormbirds.sharedcharging.equipment.config;

import cn.stormbirds.sharedcharging.common.utils.IdCenter;
import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySources;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * cn.stormbirds.sharedcharging.web.config.security
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/9/23 13:14
 */
@Configuration
@EnableNacosConfig(globalProperties = @NacosProperties(serverAddr = "wifi.stormbirds.cn:8848",namespace = "2e4cb133-cb3c-49f2-ae3a-f9395a70fc89"))
@NacosPropertySources({

        /*
         * Nacos 控制台添加配置：
         * Data ID：shared_power_bank_web-dev.properties
         * Group：multi-data-ids
         * 配置内容：test.username=nacos_value
         */
        @NacosPropertySource(dataId = "shared_power_bank_equipment-dev.properties", autoRefreshed = true),

        /*
         * 1. 本地安装 MySQL
         * 2. Nacos 控制台添加配置：
         * Data ID：datasource.properties
         * Group：multi-data-ids
         * 配置内容示例：
         *   spring.datasource.url=jdbc:mysql://localhost:3306/test?useSSL=false
         *   spring.datasource.username=root
         *   spring.datasource.password=root
         *   spring.datasource.initial-size=10
         *   spring.datasource.max-active=20
         */
        @NacosPropertySource(dataId = "datasource.properties", autoRefreshed = true),

        /*
         * Redis
         * Data ID：redis.properties
         * Group：multi-data-ids
         * 配置内容示例：
         *   spring.redis.host=localhost
         *   spring.redis.password=20190101
         *   spring.redis.timeout=5000
         *   spring.redis.max-idle=5
         *   spring.redis.max-active=10
         *   spring.redis.max-wait=3000
         *   spring.redis.test-on-borrow=false
         */
        @NacosPropertySource(dataId = "redis.properties", autoRefreshed = true)
})
@EnableDubbo
@EnableDubboConfig()
public class EquipmentNacosDubboConfig {
    @NacosValue(value = "${users.idcenter.datacenterId:1}")
    private long dataCenterId;

    @Bean
    public IdCenter idCenter(){
        return IdCenter.INSTENSE.init(dataCenterId,0,"equipment service Id生成器服务");
    }
}
