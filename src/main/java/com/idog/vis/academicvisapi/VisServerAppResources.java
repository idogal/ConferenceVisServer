/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.idog.vis.academicvisapi.beans.AcademicApiResponse;
import com.idog.vis.academicvisapi.beans.AcademicApiResponseDeserializer;
import java.io.IOException;
import javax.inject.Singleton;

/**
 *
 * @author idog
 */
@Singleton
public class VisServerAppResources {
    private ConfigReader configReader;
    ObjectMapper mapper = new ObjectMapper();

    public ObjectMapper getMapper() {
        return mapper;
    }

    public ConfigReader getConfigReader() {
        return configReader;
    }
    
    public VisServerAppResources() { 
        
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);        
        SimpleModule module = new SimpleModule();
        module.addDeserializer(AcademicApiResponse.class, new AcademicApiResponseDeserializer());        
        mapper.registerModule(module);        
        
        try {
            configReader = new ConfigReader.ConfigReaderBuilder().buildDefault();
        } catch (IOException ex) {
        }        
    }    
}
