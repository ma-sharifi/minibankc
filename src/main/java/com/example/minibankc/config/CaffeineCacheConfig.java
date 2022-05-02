package com.example.minibankc.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 5/1/22
 * This cache used for handle Idempotence and rate control
 */
@Configuration
public class CaffeineCacheConfig {

    /**
     * This is the main configuration that will control caching behavior such as expiration,
     * cache size limits, and more
     */
    @Bean
    public Cache<String, Integer> caffeineConfig() {
        return Caffeine
                .newBuilder()
                .initialCapacity(100)
                .maximumSize(500)
                .expireAfterWrite(1, TimeUnit.MINUTES).build();
    }

}
