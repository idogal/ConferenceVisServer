/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi;

import com.idog.vis.academicvisapi.resources.*;
import javax.ws.rs.ApplicationPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;
//import org.glassfish.jersey.jackson.JacksonFeature;
//import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author idog
 */
@ApplicationPath("/")
public class VisServerApp extends ResourceConfig {

    private static final Logger LOGGER = LogManager.getLogger("VisApi");
    
    public VisServerApp() {
        
        super (
            ApiResource.class,
            NetworkResource.class
                
            // Jackson support
            //JacksonFeature.class
        );
        
        this.register(new VisServerAppBinder());
        this.register(new VisServerAppRequestBinder());        
        this.register(new CORSFilter());
        
        LOGGER.info("Created a Jersy Application: VisServerApp");
    }
}
