package com.stone.demo.sign.configure;

import lombok.Data;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;


@Data
@Configuration
@AutoConfigureAfter({RedisPoolConfig.class,RedisServerConfig.class})
public class RedisConnectorFactory {


    @Bean
    public JedisConnectionFactory jedisConnectionFactory(RedisStandaloneConfiguration redisConfiguration,
                                                         JedisPoolConfig redisPoolConfig) {
        JedisConnectionFactory jedisConnectionFactory= new JedisConnectionFactory(redisConfiguration);
        jedisConnectionFactory.setPoolConfig(redisPoolConfig);
        return jedisConnectionFactory;
    }
}
