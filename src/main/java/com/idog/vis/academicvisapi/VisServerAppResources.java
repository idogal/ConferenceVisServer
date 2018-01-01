/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi;

import java.io.IOException;
import javax.inject.Singleton;

/**
 *
 * @author idog
 */
@Singleton
public class VisServerAppResources {
    private ConfigReader configReader;

    public VisServerAppResources() { 
        
        try {
            configReader = new ConfigReader.ConfigReaderBuilder().buildDefault();
        } catch (IOException ex) {
        }        
    }    
}
