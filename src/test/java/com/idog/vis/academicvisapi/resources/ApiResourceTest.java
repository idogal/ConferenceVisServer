/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.Assert;

/**
 *
 * @author idoga
 */
public class ApiResourceTest extends JerseyTest {
    
    @ClassRule
    public static ApiTestResources testResources = new ApiTestResources();

    @Override
    protected Application configure() {
        forceSet(TestProperties.CONTAINER_PORT, "0");
        return testResources.getResourceConfig();        
    }

    @Test
    public void statusCodeTest() {

        WebTarget target = target("msapi/papers");
        String get = target.request().get(String.class);
        Assert.assertNotNull(get);
    }

}
