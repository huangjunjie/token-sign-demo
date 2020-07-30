package com.stone.demo.sign.configure;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;

@Data
@Slf4j
@ConfigurationProperties(prefix ="spring.redis.jedis.pool")
@EnableConfigurationProperties({RedisPoolConfig.class})
@Configuration
public class RedisPoolConfig {

    private String maxActive;

    private String maxWait;

    private String maxIdle;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setMaxIdle(Integer.valueOf(maxIdle.trim()));
        jedisPoolConfig.setMaxWaitMillis(Integer.valueOf(maxWait.trim()));
        jedisPoolConfig.setMaxTotal(Integer.valueOf(maxActive.trim()));
        return jedisPoolConfig;
    }

}
