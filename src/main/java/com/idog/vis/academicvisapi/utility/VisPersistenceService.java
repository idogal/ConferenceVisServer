/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.utility;

import com.idog.vis.academicvisapi.resources.ApiResourceRequest;
import com.idog.vis.academicvisapi.resources.ApiResourceResponse;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import java.util.LinkedList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.bson.Document;

/**
 *
 * @author idoga
 */
public class VisPersistenceService implements VisPersistence {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(VisPersistenceService.class);

    private ApiCache apiCache;
    private MongoClient mongoClient;

    private final String DB_NAME = "msapi";
    private final String CONFPAPERS_COLLECTION_NAME = "conf_responses";
    private final String PAPERS_COLLECTION_NAME = "paper_responses";

    public VisPersistenceService(ApiCache apiCache, MongoClient mongoClient) {
        this.apiCache = apiCache;
        this.mongoClient = mongoClient;

        LOGGER.info("Initiated a VisPersistenceService");
    }

    @Override
    public String getMsApiResponse(String conference, String year, int count) {
        LOGGER.info("Querying the persistence layer for: year = {}, conference = {}, count = {}", year, conference, count);

        // First, try to get from the cache
        ApiResourceRequest apiRequest = new ApiResourceRequest(year, count);
        apiRequest.setConferenceName(conference);
        String msApiResponseJson = apiCache.getChasePapersResponse(apiRequest);
        if (msApiResponseJson != null) {
            LOGGER.debug("Recovered from cache ({}, {}, {}).", year, conference, count);
            return msApiResponseJson;
        }

        // If there's not cache, try to get from the DB    
        LOGGER.debug("Recovered {} documents from the DB ({}, {}, {}).", year, conference, count);
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<Document> collection = database.getCollection(CONFPAPERS_COLLECTION_NAME);
        FindIterable<Document> documents = collection.find(
                and(eq("conference", conference),
                        eq("year", year),
                        eq("count", count)
                )
        );

        List<String> jsons = new LinkedList<>();
        Block<Document> block = new Block<Document>() {
            @Override
            public void apply(Document doc) {
                LOGGER.debug("Getting the response out of the MongoDB Document");
                String response = doc.get("response", String.class);
                jsons.add(response);
            }
        };

        documents.forEach(block);

        LOGGER.debug("Recovered {} json objects ({}, {}, {})", jsons.size(), year, conference, count);
        if (jsons.size() != 1) {
            return "";
        } else {
            String j = jsons.get(0);

            apiRequest.setConferenceName(conference);
            apiCache.putChasePapersResponse(apiRequest, j);

            return j;
        }
    }

    @Override
    public void storeMsApiResponse(String conference, String year, int count, String response) {
        LOGGER.info("Storing the reponse for: year = {}, conference = {}, count = {}", year, conference, count);
        // Store in DB
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<Document> collection = database.getCollection(CONFPAPERS_COLLECTION_NAME);

        Document document = new Document();
        document.append("conference", conference);
        document.append("year", year);
        document.append("count", count);
        document.append("response", response);

        collection.insertOne(document);
    }

    @Override
    public String getMsApiResponse(String paperId) {
        LOGGER.info("Querying the persistence layer for: paperId = {}", paperId);

        // First, try to get from the cache
        String msApiResponseJson = apiCache.getByIdResponse(paperId);
        if (msApiResponseJson != null) {
            LOGGER.debug("Recovered from cache ({}).", paperId);
            return msApiResponseJson;
        }

        // If there's not cache, try to get from the DB    
        LOGGER.debug("Recovered {} documents from the DB ({}).", paperId);
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<Document> collection = database.getCollection(PAPERS_COLLECTION_NAME);
        FindIterable<Document> documents = collection.find(
                eq("paperId", paperId)
        );

        List<String> jsons = new LinkedList<>();
        Block<Document> block = new Block<Document>() {
            @Override
            public void apply(Document doc) {
                LOGGER.debug("Getting the response out of the MongoDB Document");
                String response = doc.get("response", String.class);
                jsons.add(response);
            }
        };

        documents.forEach(block);

        LOGGER.debug("Recovered {} json objects ({})", jsons.size(), paperId);
        if (jsons.size() != 1) {
            return "";
        } else {
            String j = jsons.get(0);
            apiCache.putByIdResponse(paperId, j);
            return j;
        }
    }

    @Override
    public void storeMsApiResponse(String paperId, String response) {
        LOGGER.info("Storing the reponse for: id = {}", paperId);
        
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<Document> collection = database.getCollection(PAPERS_COLLECTION_NAME);

        Document document = new Document();
        document.append("paperId", paperId);
        document.append("response", response);

        collection.insertOne(document);
    }

}
