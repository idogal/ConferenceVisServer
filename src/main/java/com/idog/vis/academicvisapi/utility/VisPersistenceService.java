/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.utility;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import java.util.LinkedList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author idoga
 */
public class VisPersistenceService implements VisPersistence {

    private ApiCache apiCache;
    private MongoClient mongoClient;

    private final String DB_NAME = "msapi";
    private final String CONFPAPERS_COLLECTION_NAME = "conf_responses";
    private final String PAPERS_COLLECTION_NAME = "paper_responses";

    public VisPersistenceService(ApiCache apiCache, MongoClient mongoClient) {
        this.apiCache = apiCache;
        this.mongoClient = mongoClient;
    }

    @Override
    public String getMsApiResponse(String conference, String year, int count) {
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
                String response = doc.get("response", String.class);
                jsons.add(response);
            }
        };

        documents.forEach(block);

        if (jsons.size() != 1) {
            return "";
        } else {
            return jsons.get(0);
        }
    }

    @Override
    public void storeMsApiResponse(String conference, String year, int count, String response) {
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
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<Document> collection = database.getCollection(PAPERS_COLLECTION_NAME);
        FindIterable<Document> documents = collection.find(
                eq("paperId", paperId)
        );

        List<String> jsons = new LinkedList<>();
        Block<Document> block = new Block<Document>() {
            @Override
            public void apply(Document doc) {
                String response = doc.get("response", String.class);
                jsons.add(response);
            }
        };

        documents.forEach(block);

        if (jsons.size() != 1) {
            return "";
        } else {
            return jsons.get(0);
        }
    }

    @Override
    public void storeMsApiResponse(String paperId, String response) {
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<Document> collection = database.getCollection(PAPERS_COLLECTION_NAME);

        Document document = new Document();
        document.append("paperId", paperId);
        document.append("response", response);

        collection.insertOne(document);
    }

}
