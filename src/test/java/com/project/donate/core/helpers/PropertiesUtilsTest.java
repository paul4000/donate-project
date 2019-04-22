package com.project.donate.core.helpers;

import org.junit.Test;


public class PropertiesUtilsTest {

    @Test
    public void test() {
        PropertiesUtils.getPropertyFromConfig("path.blockchain.keystore");
    }
}