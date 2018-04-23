/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources;

import com.idog.vis.academicvisapi.HttpServletRequestFactory;
import com.idog.vis.academicvisapi.VisServerAppResources;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.naming.NamingContext;
import org.apache.naming.NamingEntry;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.TestProperties;
import org.junit.rules.ExternalResource;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author idoga
 */
public class ApiTestResources extends ExternalResource {

    public ResourceConfig getResourceConfig() {
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
            Logger.getLogger(ApiResourceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.registerClasses(ApiResource.class);
        resourceConfig.register(new TestsAbstractBinder(initCtx));

        return resourceConfig;
    }

}
