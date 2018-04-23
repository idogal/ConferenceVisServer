/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi;

import javax.servlet.http.HttpServletRequest;
import org.glassfish.hk2.api.Factory;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author idoga
 */
public class HttpServletRequestFactory implements Factory<HttpServletRequest> {

    @Override
    public HttpServletRequest provide() {
        HttpServletRequest mock = mock(HttpServletRequest.class);
        when(mock.getRequestURI()).thenReturn("testlocation");
        when(mock.getQueryString()).thenReturn("");
        
        return mock;
    }

    @Override
    public void dispose(HttpServletRequest instance) {
        instance = null;
    }
    
}
