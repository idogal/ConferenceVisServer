/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idog.vis.academicvisapi.VisServerAppResources;
import com.idog.vis.academicvisapi.VisServerRequestResources;
import com.idog.vis.academicvisapi.beans.AcademicApiPaper;
import com.idog.vis.academicvisapi.beans.AcademicApiPaperExtended;
import com.idog.vis.academicvisapi.beans.AcademicApiResponse;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;

/**
 *
 * @author idoga
 */
@Path("/demo")
public class ApiResource {

    // HARD CODED VALUES
    private final String MS_COGNITIVE_API_TARGET = "https://westus.api.cognitive.microsoft.com";
    private final String ACEDEMIC_API_EVALUATE_PATH = "academic/v1.0/evaluate";
    private final String ACADEMIC_API_SUBSCRIPTION_KEY = "e1878999137d481089b706561bd0f5be";
    private final String EXPECTED_BV_VALUE = "WORKSHOP ON COOPERATIVE AND HUMAN ASPECTS OF SOFTWARE ENGINEERING";
    private final String EXPECTED_CN_VALUE = "chase";
    
    @Inject private VisServerRequestResources requestResources;
    @Inject private VisServerAppResources appResources;    
    @Context ServletContext servletContext;
    @Context HttpServletRequest servletRequest;
    
    private static final Logger LOGGER = LogManager.getLogger("VisApi");

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChasePapers(
            @DefaultValue("1000") @QueryParam("Count") int count,
            @DefaultValue("") @QueryParam("Year") String year,
            @DefaultValue("false") @QueryParam("NoCache") boolean noCache) throws IOException {

        LOGGER.info("Request recieved: {} {}", servletRequest.getRequestURI(), servletRequest.getQueryString());
        ApiResourceRequest request = new ApiResourceRequest(year, count);
        
        List<AcademicApiPaper> papers = null;
        ApiResourceResponse cachedResponse = null;
        if (!noCache) {
            cachedResponse = appResources.getFromCache(request);
            papers = (cachedResponse == null) ? null : cachedResponse.getPapers();
            if (papers != null && !papers.isEmpty()) {
                LOGGER.info("Responding with cached items ({}).", papers.size());
                return Response.ok().entity(papers).build();
            }
        }
        
        LOGGER.debug("No cache. Getting items...");
        if (!year.isEmpty()) {
            LOGGER.debug("Got " + count + " papers for year: " + year);
            papers = getPapersFromApi(year, count);
        } else {
            Year thisYear = Year.now();

            for (int i = 2011; i < thisYear.getValue(); i++) {
                LOGGER.debug("Got " + count + " papers for year: " + i);
                papers = getPapersFromApi(String.valueOf(i), count);
            }
        }

        cachedResponse = new ApiResourceResponse(papers);
        appResources.addToCache(request, cachedResponse);
        LOGGER.info("Responding with new items({}).", papers.size());
        return Response.ok().entity(papers).build();
    }

    private List<AcademicApiPaper> getPapersFromApi(String year, int count) throws IOException {
        List<AcademicApiPaper> papers = new ArrayList<>();
        
        papers.addAll(getPapersFromApiByExtendedProps(year, count));
        papers.addAll(getPapersFromApiByConferenceName(year, count));
        
        LOGGER.info("{} papers were retrieved in total.", papers.size());
        return papers;
    }
    
    private String queryTheAcademicApi(String conferenceName, String year, int count) {
        LOGGER.debug("Sending a query to the MS Academic API.");
        
        ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);
        
        String expr = "Composite(C.CN='" + conferenceName + "')";
        if (year != null && !year.isEmpty()) {
            expr = "And(" + expr + ", Y=" + year + ")";
        }

        WebTarget webTarget = client.target(MS_COGNITIVE_API_TARGET);
        webTarget = webTarget.path(ACEDEMIC_API_EVALUATE_PATH);
        webTarget = webTarget.queryParam("expr", expr);
        webTarget = webTarget.queryParam("attributes", "Ti,Id,Y,E");
        webTarget = webTarget.queryParam("Count", count);

        LOGGER.debug("Target: {}", webTarget.toString());
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("Ocp-Apim-Subscription-Key", ACADEMIC_API_SUBSCRIPTION_KEY);

        try {
            Response response = invocationBuilder.get();
            int status = response.getStatus();
            LOGGER.debug("Response status: {} - {}", String.valueOf(status), response.getStatusInfo().getReasonPhrase());
            if (status < 200 || status > 300) {
                LOGGER.error(response.getStatusInfo().getReasonPhrase());
                throw new WebApplicationException(response.getStatusInfo().getReasonPhrase());
            }
            
            LOGGER.debug("Entity was sucessfully retrieved from the API.");
            return response.readEntity(String.class);
            
        } catch (ProcessingException procEx) {
            LOGGER.error(procEx.getMessage());
            throw procEx;
        }
    }
    
    private List<AcademicApiPaper> getPapersFromApiByConferenceName(String year, int count) throws IOException {
        LOGGER.debug("Trying to get papers by the C.CN property.");

        String entityString = queryTheAcademicApi(EXPECTED_CN_VALUE, year, count);

        ObjectMapper mapper = appResources.getMapper();
        AcademicApiResponse readValue = mapper.readValue(entityString, AcademicApiResponse.class);
        LOGGER.debug("Response was serialised into an AcademicApiResponse successfully");
        
        LOGGER.debug("{} papers were found with '{}' value in the C.CN field", readValue.entities.size(), EXPECTED_CN_VALUE);
        return readValue.entities;
    }
    
    private List<AcademicApiPaper> getPapersFromApiByExtendedProps(String year, int count) throws IOException {
        LOGGER.debug("Trying to get papers by the Extended properties.");
        
        String entityString = queryTheAcademicApi("icse", year, count);        
        
        ObjectMapper mapper = appResources.getMapper();
        AcademicApiResponse readValue = mapper.readValue(entityString, AcademicApiResponse.class);
        LOGGER.debug("Response was serialised into an AcademicApiResponse successfully");
        
        List<AcademicApiPaper> papers = readValue.entities;
        Stream<AcademicApiPaper> filteredPapers
                = papers.stream()
                        .filter(paper -> paper.getExtendedProperties() != null)
                        .filter(paper -> 
                                paper.getExtendedProperties().getBv().toUpperCase().contains(EXPECTED_BV_VALUE)
                );
                
        papers = filteredPapers.collect(Collectors.toList());
        LOGGER.debug("{} papers were found with '{}' value in the BV field", papers.size(), EXPECTED_BV_VALUE);
        
        return papers;
    }
}
