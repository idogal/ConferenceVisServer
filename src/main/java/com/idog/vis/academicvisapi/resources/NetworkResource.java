/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.idog.vis.academicvisapi.VisServerAppResources;
import com.idog.vis.academicvisapi.beans.AcademicApiPaper;
import com.idog.vis.academicvisapi.resources.model.VisCouplingGraphEdge;
import com.idog.vis.academicvisapi.resources.model.VisCouplingGraphEdgeSerializer;
import com.idog.vis.academicvisapi.resources.model.VisCouplingGraphNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author idoga
 */
@Path("/network")
public class NetworkResource {

    @Inject
    private VisServerAppResources appResources;
    @Context
    ServletContext servletContext;
    @Context
    HttpServletRequest servletRequest;

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger("VisApi");
    private final VisNetworkProcessor visProc = new VisNetworkProcessor();

    /**
     * Sample request: http://localhost:8097/VisAPI/network/edges?year=2011
     *
     * @param year
     * @param noCache
     * @return
     */
    @Path("edges")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEdges(
            @DefaultValue("") @QueryParam("Year") String year,
            @DefaultValue("false") @QueryParam("NoCache") boolean noCache) {
        LOGGER.info("Request recieved: {} {}", servletRequest.getRequestURI(), servletRequest.getQueryString());
        
        javax.naming.Context initCtx;
        try {
            initCtx = new InitialContext();
            javax.naming.Context envCtx = (javax.naming.Context) initCtx.lookup("java:comp/env");
            String s = (String) envCtx.lookup("mongodb.host");
        } catch (NamingException ex) {
            Logger.getLogger(VisServerAppResources.class.getName()).log(Level.SEVERE, null, ex);
        }        

        VisMsApiService msApiService = new VisMsApiService(appResources);
        List<VisCouplingGraphEdge> processPapersList = null;
        try {
            List<AcademicApiPaper> chasePapers = msApiService.getChasePapersAsList(year, noCache);
            processPapersList = processPapersList(chasePapers);

            processPapersList = processPapersList.stream()
                    .filter((VisCouplingGraphEdge edge) -> edge.getCoupling().getCouplingStrength() > 0)
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
            Response.serverError();
        }

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        Class<List<VisCouplingGraphEdge>> classType = (Class<List<VisCouplingGraphEdge>>) processPapersList.getClass();
        module.addSerializer(classType, new VisCouplingGraphEdgeSerializer());
        mapper.registerModule(module);

        String serialized = "";
        try {
            serialized = mapper.writeValueAsString(processPapersList);
        } catch (JsonProcessingException ex) {
            LOGGER.error(ex.getMessage());
            Response.serverError();
        }

        return Response.ok().entity(serialized).build();
    }

    @Path("nodes")
    @GET
    @Produces(MediaType.APPLICATION_JSON)    
    public Response getNodes(
            @DefaultValue("") @QueryParam("Year") String year,
            @DefaultValue("false") @QueryParam("NoCache") boolean noCache) {

        LOGGER.info("Request recieved: {} {}", servletRequest.getRequestURI(), servletRequest.getQueryString());

        VisMsApiService msApiService = new VisMsApiService(appResources);
        List<VisCouplingGraphNode> graphNodes = null;
        try {
            List<AcademicApiPaper> chasePapers = msApiService.getChasePapersAsList(year, noCache);
            graphNodes = getAuthorsList(chasePapers);

        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
            Response.serverError();
        }

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        String serialized = "";
        try {
            serialized = mapper.writeValueAsString(graphNodes);
        } catch (JsonProcessingException ex) {
            LOGGER.error(ex.getMessage());
            Response.serverError();
        }

        return Response.ok().entity(serialized).build();
    }

    public List<VisCouplingGraphEdge> processPapersList(List<AcademicApiPaper> chasePapers) {
        List<VisCouplingGraphEdge> edges = visProc.processSimpleCoupling(chasePapers);
        return edges;
    }
    
    public List<VisCouplingGraphNode> getAuthorsList(List<AcademicApiPaper> chasePapers) {
        Set<VisCouplingGraphNode> nodesSet = visProc.deriveAuthorsSet(chasePapers);
        List<VisCouplingGraphNode> nodes = new ArrayList<>();
        nodes.addAll(nodesSet);
        return nodes;
    }    
}
