package com.example.minibankc.util.jmx;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.util.Arrays;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 5/1/22
 */
@Component
@ManagedResource(objectName="minibankc-manamgement:name=MiniBankC-XManagement", description="My Managed Bean")
@Slf4j
public class ApplicationManagementMBean  {

    @Autowired
    private  Cache<String, Integer> cache;

    @ManagedOperation
    public void clearCaffeineCache() {
        cache.cleanUp();
        log.info("#Caffeine Cache Cleared");
    }
}
