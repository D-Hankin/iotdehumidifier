package com.iotdehumidifier.iotdehumidifier.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    
    @Value("${PRIVATE_KEY}")
    private String privateKey;
    
    @Value("${JWT_ISSUER}")
    private String jwtIssuer;

    public String getPrivateKey() {
        return privateKey;
    }

    public String getJwtIssuer() {
        return jwtIssuer;
    }
}
