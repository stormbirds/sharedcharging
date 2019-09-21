package cn.stormbirds.sharedcharging.users;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
//import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@EnableDubbo(scanBasePackages = "cn.stormbirds.sharedcharging.users.service.impl")

@SpringBootApplication
public class UsersApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersApplication.class, args);
    }

}
