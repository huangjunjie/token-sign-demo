package com.stone.demo.sign.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;

@Data
@EnableConfigurationProperties({RedisServerConfig.class})
@ConfigurationProperties(prefix ="spring.redis")
@Configuration
public class RedisServerConfig {

    private  String host;

    private  Integer port;

    @Bean
    public RedisStandaloneConfiguration getRedisConfiguration() {
        return new RedisStandaloneConfiguration(host, port);
    }
}
