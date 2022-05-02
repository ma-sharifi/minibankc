package com.example.minibankc.util.jmx;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 5/1/22
 */
@Component
@ManagedResource(objectName = "minibankc-xmanamgement:name=MiniBankC-XManagement", description = "MiniBankC Management Bean")
@Slf4j
public class ApplicationManagementMBean {

    private final Cache<String, Integer> cache;

    public ApplicationManagementMBean(Cache<String, Integer> cache) {
        this.cache = cache;
    }

    @ManagedOperation(description = "#Clearing Caffeine Cache")
    public void clearCaffeineCache() {
        cache.invalidateAll();
        log.info("#Caffeine Cache Cleared.");
    }
}
