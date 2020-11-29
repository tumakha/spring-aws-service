package spring.aws.service.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

/**
 * @author Yuriy Tumakha
 */
@EnableSwagger2
@Configuration
@AllArgsConstructor
public class SwaggerConfig {

    private final Environment env;

    @Bean
    public Docket api() {
        return new Docket(SWAGGER_2)
                .groupName("v1")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .paths(PathSelectors.regex("/v1/.*"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Sample Service API",
                "AWS Service Demo. Active profiles: " + Arrays.toString(env.getActiveProfiles()),
                "1.0",
                "",
                new Contact("Yuriy Tumakha", "", "Yuriy.Tumakha@iplato.com"),
                "", "", List.of());
    }

}
