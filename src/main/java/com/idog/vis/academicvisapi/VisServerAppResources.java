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
import com.idog.vis.academicvisapi.utility.VisPersistence;
import com.idog.vis.academicvisapi.utility.VisPersistenceService;
import com.mongodb.MongoClient;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Singleton;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author idog
 */
@Singleton
public class VisServerAppResources {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger("VisApi");

    private ConfigReader configReader;
    private ObjectMapper mapper = new ObjectMapper();
    //private ApiCache cache = new ApiCache();
    //private MongoClient mongoClient;
    private VisPersistence visPersistenceService;

    public ObjectMapper getMapper() {
        return mapper;
    }

    public ConfigReader getConfigReader() {
        return configReader;
    }

    public VisPersistence getVisPersistenceService() {
        return visPersistenceService;
    }

//    public ApiCache getCache() {
//        return cache;
//    }
//
//    public ApiResourceResponse getChasePaperFromCache(ApiResourceRequest key) {
//        return cache.getChasePapersResponse(key);
//    }
//
//    public AcademicApiResponse getPaperByIdFromCache(String key) {
//        return cache.getByIdResponse(key);
//    }
//
//    public void addChasePapersToCache(ApiResourceRequest key, ApiResourceResponse value) {
//        cache.putChasePapersResponse(key, value);
//    }
//
//    public void addPaperByIdToCache(String key, AcademicApiResponse value) {
//        cache.putByIdResponse(key, value);
//    }
    
    public VisServerAppResources() {

        LOGGER.info("Initialising the VisServerAppResources object");

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(AcademicApiResponse.class, new AcademicApiResponseDeserializer());
        mapper.registerModule(module);

        LOGGER.trace("Configured the ObjectMapper for: FAIL_ON_UNKNOWN_PROPERTIES");
        LOGGER.trace("Registered a Deserializer: AcademicApiResponseDeserializer");

        String mongoHost = "";
        int mongoPort;
        Context initCtx;
        try {
            initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            mongoHost = (String) envCtx.lookup("mongodb.host");
            mongoPort = (Integer) envCtx.lookup("mongodb.port");
        } catch (NamingException ex) {
            LOGGER.error(ex);
            mongoHost = "localhost";
            mongoPort = 27017;
        }

        LOGGER.trace("MongoDB: Host={}, Port={}", mongoHost, mongoPort);

        MongoClient mongoClient = new MongoClient(mongoHost, mongoPort);

        visPersistenceService
                = new VisPersistenceService(new ApiCache(), mongoClient);
        
        LOGGER.info("Finished initialising the VisServerAppResources object");
    }
}
