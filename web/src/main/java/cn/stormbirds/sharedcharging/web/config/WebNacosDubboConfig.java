package cn.stormbirds.sharedcharging.web.config;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * cn.stormbirds.sharedcharging.web.config.security
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/9/23 13:14
 */
@NacosPropertySource(dataId = "shared_power_bank_web-dev.properties", autoRefreshed = true)
@EnableDubbo
@Configuration
public class WebNacosDubboConfig {

}
