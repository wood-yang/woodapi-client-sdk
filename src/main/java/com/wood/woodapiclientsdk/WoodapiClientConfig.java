package com.wood.woodapiclientsdk;

import com.wood.woodapiclientsdk.client.WoodapiClient;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ComponentScan
public class WoodapiClientConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public WoodapiClient woodapiClient() {
        return new WoodapiClient(accessKey, secretKey);
    }
}
