/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.beans;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.idog.vis.academicvisapi.VisServerAppResources;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.inject.Inject;

/**
 *
 * @author idoga
 */
public class AcademicApiResponseDeserializer extends StdDeserializer<AcademicApiResponse> {

    public AcademicApiResponseDeserializer() {
        this(null);
    }

    public AcademicApiResponseDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public AcademicApiResponse deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
        AcademicApiResponse response = new AcademicApiResponse();

        try {
            JsonNode responseNode = jp.getCodec().readTree(jp);

            // Response
            String expr = responseNode.get("expr").textValue();
            response.expr = expr;

            JsonNode entitiesNode = responseNode.get("entities");
            response.entities = new ArrayList<>();

            // Paper Entities
            Iterator<JsonNode> paperElements = entitiesNode.elements();
            while (paperElements.hasNext()) {
                AcademicApiPaper paper = new AcademicApiPaper();

                // Paper
                JsonNode paperNode = paperElements.next();
                if (paperNode == null) {
                    continue;
                }
                JsonNode tiNode = paperNode.get("Ti");
                JsonNode idNode = paperNode.get("Id");
                JsonNode yearNode = paperNode.get("Y");
                paper.setTitle((tiNode != null) ? tiNode.asText() : null);
                paper.setId((idNode != null) ? idNode.asInt() : null);
                paper.setYear((yearNode != null) ? yearNode.asText() : null);
                
                JsonNode refNodes = paperNode.get("RId");
                if (refNodes.isArray()) {                    
                    for (JsonNode refNode : refNodes) {
                        paper.addReference(refNode.asLong());
                    }
                }
                
                JsonNode keywordNodes = paperNode.get("W");
                if (keywordNodes.isArray()) {                    
                    for (JsonNode keywordNode : keywordNodes) {
                        paper.addKeyword(keywordNode.asText());
                    }
                }                
                
                //////////////////////////
                //Composited Properties //
                //////////////////////////
                ObjectMapper mapper = new ObjectMapper(); // Check if it's possible to use the same ObjectMapper instance in the AppResources
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);                
                
                //Authos
                JsonNode authorNodes = paperNode.get("AA");
                if (authorNodes == null) {
                    continue;
                }
                
                if (authorNodes.isArray()) {
                    for (JsonNode authorNode : authorNodes) {
                        JsonNode authorNameNode = authorNode.get("AuN");
                        JsonNode authorIdNode = authorNode.get("AuId");
                        JsonNode affNameNode = authorNode.get("AfN");
                        JsonNode affIdNode = authorNode.get("AfId");
                        JsonNode authorOrderNode = authorNode.get("S");
                        
                        String authorName = (authorNameNode != null) ? authorNameNode.asText() : null;
                        long authorId = (authorIdNode != null) ? authorIdNode.asLong() : null;
                        String affName = (affNameNode != null) ? affNameNode.asText() : null;
                        long affId = (affIdNode != null) ? affIdNode.asLong() : null;
                        int order = (authorOrderNode != null) ? authorOrderNode.asInt() : null;
                        
                        paper.addAuthor(new AcademicApiAuthor(authorName, authorId, affName, affId, order));
                    }
                }
                

                // Extended, by deserialising again
                JsonNode extendedNode = paperNode.get("E");
                if (extendedNode == null) {
                    response.entities.add(paper);
                    continue;
                }
                String extendedString = extendedNode.asText();
                AcademicApiPaperExtended paperExtended = mapper.readValue(extendedString, AcademicApiPaperExtended.class);
                paper.setExtendedProperties(paperExtended);

                response.entities.add(paper);

            }
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }

        return response;
    }

}
