package com.example.minibankc.util.jmx;

import lombok.extern.slf4j.Slf4j;

import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 5/1/22
 */

@Slf4j
public class ApplicationManagementMBean  implements ManagementMXBean {

    private final static String JMXBEAN_MINI_BANK_C = "MiniBankC-XManagement";

    private MBeanServer platformMBeanServer;

    private ObjectName objectName = null;

    @Override
    public boolean getProfile() {
        return false;
    }

    @Override
    public void clearCache() {
        log.info("Cache Cleared");
    }
}
