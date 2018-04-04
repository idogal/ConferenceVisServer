/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author idoga
 */
public class ConfigReaderTest {
    
    private static ConfigReader configReader;
    
    @BeforeClass
    public static void init() throws IOException {
        ConfigReader.ConfigReaderBuilder builder = new ConfigReader.ConfigReaderBuilder();       
        configReader = builder.buildDefault();
    }
    
    @Test
    public void getPropsTest() {
        String mongoHost = configReader.getMongoHost();
        int mongoPort = configReader.getMongoPort();
        
        org.junit.Assert.assertNotNull(mongoPort);
        org.junit.Assert.assertNotNull(mongoHost);
        org.junit.Assert.assertEquals(configReader.getProps().size(), 2);
    }
}
