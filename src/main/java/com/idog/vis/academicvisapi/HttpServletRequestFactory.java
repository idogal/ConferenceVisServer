/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi;

import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.hk2.api.Factory;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author idoga
 */
public class HttpServletRequestFactory implements Factory<HttpServletRequest> {

    private static final Logger LOGGER = LogManager.getLogger(HttpServletRequestFactory.class);
    
    @Override
    public HttpServletRequest provide() {
        HttpServletRequest mock = mock(HttpServletRequest.class);
        when(mock.getRequestURI()).thenReturn("testlocation");
        when(mock.getQueryString()).thenReturn("");
        
        LOGGER.info("Providing a HttpServletRequest mock with the HttpServletRequestFactory");
        
        return mock;
    }

    @Override
    public void dispose(HttpServletRequest instance) {
        instance = null;
        
        LOGGER.info("Disposed of the HttpServletRequest");
    }
    
}
