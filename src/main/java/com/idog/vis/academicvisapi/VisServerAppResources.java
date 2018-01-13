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
import com.idog.vis.academicvisapi.resources.ApiResourceRequest;
import com.idog.vis.academicvisapi.resources.ApiResourceResponse;
import com.idog.vis.academicvisapi.utility.ApiCache;
import java.io.IOException;
import javax.inject.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author idog
 */
@Singleton
public class VisServerAppResources {
    private ConfigReader configReader;
    ObjectMapper mapper = new ObjectMapper();
    //private VisApiCache<String, List<AcademicApiPaper>> apiCache = new VisApiCache<>(10, 10, 10);
    private ApiCache cache = new ApiCache();
    
    public ObjectMapper getMapper() {
        return mapper;
    }

    public ConfigReader getConfigReader() {
        return configReader;
    }

    public ApiCache getCache() {
        return cache;
    }
    
    public ApiResourceResponse getFromCache(ApiResourceRequest key) {        
        return cache.getApiResponse(key);
    }
    
    public void addToCache(ApiResourceRequest key, ApiResourceResponse value) {
        cache.putApiResponse(key, value);
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
