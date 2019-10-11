package cn.stormbirds.sharedcharging.web.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * cn.stormbirds.sharedcharging.web.config
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/10/11 11:53
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private Contact contact = new Contact("stormbirds","https://blog.stormbirds.cn", "xbaojun@gmail.com");

    @Bean
    public Docket createRestApi() {
        String auth = "Bearer eyJhbGciOiJIUzI1NiIsInppcCI6IkRFRiJ9.eNpEjssKwjAQRf9l1gkkbdI02" +
                "bsThIorkTJJRojWtPQBgvjvVrro8h44h_uBafHgAOMrZWCwTDS2KYIzlbRlXaqyUKaQVaUYTKEf" +
                "CNwVmtPx0F7OhwZuDHKfW3oPaaTVmseFGKwTnNRG1FKLQjBIOG_A1BugjL7bhX-j68NzJ485ra9" +
                "ssIZQIo9aRq6sQY4o71x4RdFo74NS8P0BAAD__w.YsvhVPgc7Ig6KDWM_nvXPWKzB7FLiNEtf-a" +
                "ggJWBPkg";

        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("Authorization").defaultValue(auth)
                .description("令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build();

        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(pars)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("平台接口 v1.0")
                .description("平台接口")
                .contact(contact)
                .version("1.0")
                .build();
    }
}
