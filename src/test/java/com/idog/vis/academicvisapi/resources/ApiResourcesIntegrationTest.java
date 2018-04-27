/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import org.apache.naming.NamingContext;
import org.apache.naming.NamingEntry;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.Assert;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author idoga
 */
public class ApiResourcesIntegrationTest extends JerseyTest {

//    @ClassRule
//    public static ApiTestResources testResources = new ApiTestResources();
    @Override
    protected Application configure() {
        forceSet(TestProperties.CONTAINER_PORT, "0");
        Hashtable<String, Object> env = new Hashtable<>();
        HashMap<String, NamingEntry> bindings = new HashMap<>();
        bindings.put("mongodb.host", new NamingEntry("mongodb.host", "", 0));
        bindings.put("mongodb.port", new NamingEntry("mongodb.port", 0, 0));
        bindings.put("mongodb.user", new NamingEntry("mongodb.user", "", 0));
        bindings.put("mongodb.password", new NamingEntry("mongodb.password", "", 0));
        bindings.put("mongodb.db", new NamingEntry("mongodb.db", "", 0));
        bindings.put("mongodb.ssl", new NamingEntry("mongodb.ssl", false, 0));
        bindings.put("mongodb.connectionstring", new NamingEntry("mongodb.connectionstring", "mongodb+srv://admin:picard889!@cluster0-ot7ig.mongodb.net/msapi", 0));

        NamingContext nCtx = new org.apache.naming.NamingContext(env, "Catalina/localhost/VisAPI", bindings);

        InitialContext initCtx = mock(InitialContext.class);
        try {
            when(initCtx.lookup("java:comp/env")).thenReturn(nCtx);
        } catch (NamingException ex) {
            Logger.getLogger(ApiResourcesIntegrationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.registerClasses(ApiResource.class);
        resourceConfig.registerClasses(NetworkResource.class);
        resourceConfig.register(new TestsAbstractBinder(initCtx));

        return resourceConfig;
    }

    @Test
    public void statusCodeTest() {

        WebTarget target = target("msapi/papers").queryParam("year", "2011");

        Response response = target.request().get();

        // Test statuc code
        Assert.assertEquals(200, response.getStatus());

        // Test media type
        List<Object> contentType = response.getHeaders().get("Content-Type");
        boolean anyMatch = contentType.stream().anyMatch(
                (Object c) -> c.toString().equalsIgnoreCase("application/json")
        );
        Assert.assertTrue(anyMatch);

        // Test reponse payload
        Assert.assertNotNull(response);
    }

}
