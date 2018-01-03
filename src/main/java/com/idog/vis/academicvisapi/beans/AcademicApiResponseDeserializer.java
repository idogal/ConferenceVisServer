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
    public AcademicApiResponse deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonNode responseNode = jp.getCodec().readTree(jp);
        AcademicApiResponse response = new AcademicApiResponse();
        
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
            paper.title = paperNode.get("Ti").asText();
            paper.id = paperNode.get("Id").asInt();

            // Extended, by deserialising again
            JsonNode extendedNode = paperNode.get("E");
            String extendedString = extendedNode.asText();            

            ObjectMapper mapper = new ObjectMapper(); // Check if it's possible to use the same ObjectMapper instance in the AppResources
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);     
            AcademicApiPaperExtended paperExtended = mapper.readValue(extendedString, AcademicApiPaperExtended.class);  
            paper.extendedProperties = paperExtended;
            
            response.entities.add(paper);
        }
        
        return response;
    }
    
}
