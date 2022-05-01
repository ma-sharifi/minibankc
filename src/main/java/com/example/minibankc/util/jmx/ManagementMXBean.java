package com.example.minibankc.util.jmx;

import javax.management.MXBean;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 5/1/22
 */
@MXBean
public interface ManagementMXBean {
    boolean getProfile();
    void clearCache();
}
