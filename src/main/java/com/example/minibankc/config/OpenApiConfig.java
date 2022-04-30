package com.example.minibankc.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/30/22
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mini Bank C API")
                        .description("Mini Bank C API V1 Definition ")
                        .version("v1")
                        .contact(new Contact()
                                .name("Mahdi Sharifi")
                                .url("https://www.linkedin.com/in/mahdisharifi/")
                                .email("mahdi.elu@gmail.com"))
                );
    }
}
