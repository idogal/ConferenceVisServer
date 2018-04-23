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
import com.idog.vis.academicvisapi.utility.ApiCache;
import com.idog.vis.academicvisapi.utility.VisPersistence;
import com.idog.vis.academicvisapi.utility.VisPersistenceService;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.util.ArrayList;
import java.util.List;
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
        this(null);
    }
    
    public VisServerAppResources(InitialContext ctx) {
        LOGGER.info("Initialising the VisServerAppResources object");

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(AcademicApiResponse.class, new AcademicApiResponseDeserializer());
        mapper.registerModule(module);

        LOGGER.trace("Configured the ObjectMapper for: FAIL_ON_UNKNOWN_PROPERTIES");
        LOGGER.trace("Registered a Deserializer: AcademicApiResponseDeserializer");

        MongoClient mongoClient = getMongoClient(ctx);

        visPersistenceService
                = new VisPersistenceService(new ApiCache(), mongoClient);

        LOGGER.info("Finished initialising the VisServerAppResources object");
    }

    private MongoClient getMongoClient(InitialContext initCtx) {
        String mongoHost = "";
        int mongoPort;
        String mongoUser = "";
        String mongoPw = "";
        String mongoDb = "";
        Boolean mongoSSL = false;
        String mongoConnectionString = "";

        try {
            if (initCtx == null) {
                initCtx = new InitialContext();
            }

            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            mongoHost = (String) envCtx.lookup("mongodb.host");
            mongoPort = (Integer) envCtx.lookup("mongodb.port");
            mongoUser = (String) envCtx.lookup("mongodb.user");
            mongoPw = (String) envCtx.lookup("mongodb.password");
            mongoDb = (String) envCtx.lookup("mongodb.db");
            mongoSSL = (Boolean) envCtx.lookup("mongodb.ssl");
            mongoConnectionString = (String) envCtx.lookup("mongodb.connectionstring");
        } catch (NamingException ex) {
            LOGGER.error(ex);
            mongoHost = "localhost";
            mongoPort = 27017;
        }

        LOGGER.debug("MongoDB: Host={}, Port={}, User={}, Pw=, Db={}, SSL={}, ConnectionString={}", mongoHost, mongoPort, mongoUser, mongoDb, mongoSSL, mongoConnectionString);

        if (!mongoConnectionString.isEmpty()) {
            MongoClient mongoClient = new MongoClient(new MongoClientURI(mongoConnectionString));
            LOGGER.debug("MongoDB: Connected using the connection string.");
            return mongoClient;
        }

        ServerAddress mongoAddress = new ServerAddress(mongoHost);
        if (mongoPort != 0) {
            mongoAddress = new ServerAddress(mongoHost, mongoPort);
        }

        MongoCredential credential = null;

        if (!mongoUser.isEmpty()) {
            credential = MongoCredential.createCredential(mongoUser, mongoDb, mongoPw.toCharArray());

            List<MongoCredential> creds = new ArrayList<>();
            creds.add(credential);
        }

        MongoClientOptions options = null;
        if (mongoSSL) {
            options = MongoClientOptions.builder()
                    .connectTimeout(60000)
                    .sslEnabled(true).build();
        } else {
            options = MongoClientOptions.builder()
                    .connectTimeout(60000)
                    .sslEnabled(false).build();
        }

        MongoClient mongoClient = null;

        try {
            if (credential == null) {
                mongoClient = new MongoClient(mongoAddress);
            } else {
                mongoClient = new MongoClient(mongoAddress, credential, options);
            }
        } catch (RuntimeException e) {
            LOGGER.error(e);
        }

        return mongoClient;
    }
}
