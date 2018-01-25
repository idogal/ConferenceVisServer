/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources.vismodel;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.idog.vis.academicvisapi.beans.AcademicApiResponseDeserializer;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author idoga
 */
public class VisCouplingGraphEdgeSerializer extends StdSerializer<List<VisCouplingGraphEdge>> {
    //private static final Logger LOGGER = LogManager.getLogger(VisCouplingGraphEdgeSerializer.class);

    public VisCouplingGraphEdgeSerializer() {
        this(null);
    }

    public VisCouplingGraphEdgeSerializer(Class<List<VisCouplingGraphEdge>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<VisCouplingGraphEdge> values, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        
        jgen.writeStartArray();
        for (VisCouplingGraphEdge edge : values) {
            jgen.writeStartObject();
            jgen.writeStringField("authorNameA", edge.getAuthorA().getName());
            jgen.writeStringField("authorNameB", edge.getAuthorB().getName());
            jgen.writeNumberField("coupling", edge.getCouplingStrength());
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
        
    }
}
