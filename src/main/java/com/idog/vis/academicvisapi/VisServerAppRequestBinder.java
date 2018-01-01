/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 *
 * @author idog
 */
public class VisServerAppRequestBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(new VisServerRequestResources())
                .to(VisServerRequestResources.class);
    }
    
}
