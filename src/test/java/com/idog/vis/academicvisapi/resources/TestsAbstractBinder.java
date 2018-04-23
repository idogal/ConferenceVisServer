/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources;

import com.idog.vis.academicvisapi.HttpServletRequestFactory;
import com.idog.vis.academicvisapi.VisServerAppResources;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.rules.ExternalResource;

/**
 *
 * @author idoga
 */
public class TestsAbstractBinder extends AbstractBinder {

    private InitialContext ctx = null;

    public TestsAbstractBinder(InitialContext ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void configure() {
        bind(new VisServerAppResources(ctx))
                .to(VisServerAppResources.class);

        bindFactory(HttpServletRequestFactory.class).to(HttpServletRequest.class);

    }
}
