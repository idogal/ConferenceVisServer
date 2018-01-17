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
import com.idog.vis.academicvisapi.beans.AcademicApiResponse;
import java.io.IOException;
import java.time.Year;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;

/**
 *
 * @author idoga
 * @see
 * <a href="https://docs.microsoft.com/en-us/azure/cognitive-services/academic-knowledge/home"></a>
 */
@Path("/demo")
public class ApiResource {

    // HARD CODED VALUES
    private final String MS_COGNITIVE_API_TARGET = "https://westus.api.cognitive.microsoft.com";
    private final String ACEDEMIC_API_EVALUATE_PATH = "academic/v1.0/evaluate";
    private final String ACADEMIC_API_SUBSCRIPTION_KEY = "e1878999137d481089b706561bd0f5be";
    private final String EXPECTED_BV_VALUE = "WORKSHOP ON COOPERATIVE AND HUMAN ASPECTS OF SOFTWARE ENGINEERING";
    private final String EXPECTED_CN_VALUE = "chase";

    @Inject
    private VisServerRequestResources requestResources;
    @Inject
    private VisServerAppResources appResources;
    @Context
    ServletContext servletContext;
    @Context
    HttpServletRequest servletRequest;

    private static final Logger LOGGER = LogManager.getLogger("VisApi");

    @Path("")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChasePapers(
            @DefaultValue("") @QueryParam("id") String id) throws IOException {
        
        LOGGER.info("Request recieved: {} {}", servletRequest.getRequestURI(), servletRequest.getQueryString());

        if (id.isEmpty()) {
            LOGGER.warn("id parameter must be passed");
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"id parameter must be passed\"}").build();
        }

        List<AcademicApiPaper> chasePapers;
        try {
            chasePapers = getChasePaperById(id);
        } catch (ExecutionException ex) {
            LOGGER.error(ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        LOGGER.info("Responding...");
        return Response.ok().entity(chasePapers).build();
    }

    @Path("/chase_papers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChasePapers(
            @DefaultValue("") @QueryParam("Year") String year,
            @DefaultValue("false") @QueryParam("NoCache") boolean noCache) {
        
        LOGGER.info("Request recieved: {} {}", servletRequest.getRequestURI(), servletRequest.getQueryString());
        
        List<AcademicApiPaper> allPapers = new ArrayList<>();

        try {
            List<AcademicApiPaper> chasePapersIds = getChasePapersIds(1000, year, noCache);
            allPapers = getChasePaperByIdMultiThreaded(chasePapersIds);
            
        } catch (IOException | RuntimeException ex) {
            LOGGER.error(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        LOGGER.info("Responding...");
        return Response.ok().entity(allPapers).build();
    }

    private List<AcademicApiPaper> getChasePaperByIdMultiThreaded(List<AcademicApiPaper> chasePapersIds) {

        LOGGER.info("Trying to get full detials for papers list");        
        List<AcademicApiPaper> allPapers = new ArrayList<>();

        int startFrom = 0, batchSize = 10;
        boolean papersRemaining = true;
        while (papersRemaining) {
            int currentFinishPosition = startFrom + batchSize - 1;
            if (currentFinishPosition >= chasePapersIds.size() - 1) {
                papersRemaining = false;
                currentFinishPosition = chasePapersIds.size() - 1;
            }

            // Process current batch
            ExecutorService executor = Executors.newFixedThreadPool(currentFinishPosition - startFrom);
            List<Future<List<AcademicApiPaper>>> tasks = new ArrayList<>();

            for (int i = startFrom; i <= currentFinishPosition; i++) {
                AcademicApiPaper chasePaper = chasePapersIds.get(i);
                
                // Create thread
                Future<List<AcademicApiPaper>> getPapersTask = executor.submit(() -> {
                    for (int j = 0; j < 10; j++) {
                        try {
                            long id = chasePaper.getId();
                            return getChasePaperById(String.valueOf(id));
                        } catch (WebApplicationException webEx) {
                            LOGGER.debug("Request failed, trying again (" + (j + 1) + ")");
                        } catch (IOException | ExecutionException ex) {
                            LOGGER.warn(ex.getMessage());
                        }
                        TimeUnit.MILLISECONDS.sleep(j * 150);
                    }

                    LOGGER.error("Could not get response after multiple trials");
                    return null;
                });

                tasks.add(getPapersTask);
            }

            // Read from current batch
            for (Future<List<AcademicApiPaper>> task : tasks) {
                try {
                    List<AcademicApiPaper> papersFromThread = task.get();
                    if (papersFromThread != null) {
                        allPapers.addAll(papersFromThread);                        
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    LOGGER.error(ex.getMessage());
                }
            }

            executor.shutdown();
            startFrom = startFrom + batchSize;
        }

        LOGGER.info("Got {} papers", allPapers.size());
        return allPapers;
    }

    private List<AcademicApiPaper> getChasePaperById(String id) throws IOException, ExecutionException, WebApplicationException {
        LOGGER.info("Building a request by an ID for: {}", id);
        
        String expr = "Id=" + id;
        String attributes = "Id,Ti,AA.AuN,AA.AuId,AA.AfN,AA.AfId,AA.S,F.FN,F.FId,RId,W";
        List<AbstractMap.SimpleEntry<String, Object>> params = new ArrayList<>();
        params.add(new AbstractMap.SimpleEntry<>("expr", expr));
        params.add(new AbstractMap.SimpleEntry<>("attributes", attributes));
        String entityString = queryTheAcademicApi(params);

        ObjectMapper mapper = appResources.getMapper();
        AcademicApiResponse readValue;
        readValue = mapper.readValue(entityString, AcademicApiResponse.class);

        LOGGER.debug("Response was serialised into an AcademicApiResponse successfully");

        LOGGER.info("{} papers were found with '{}' id", readValue.entities.size(), id);
        return readValue.entities;
    }

    private List<AcademicApiPaper> getChasePapersIds(int count, String year, boolean noCache) throws IOException {

        LOGGER.info("Trying getting papers ids list...");        
        ApiResourceRequest request = new ApiResourceRequest(year, count);

        List<AcademicApiPaper> papers;
        ApiResourceResponse cachedResponse = null;
        if (!noCache) {
            cachedResponse = appResources.getFromCache(request);
            papers = (cachedResponse == null) ? null : cachedResponse.getPapers();
            if (papers != null && !papers.isEmpty()) {
                LOGGER.info("Got ids for {} cached papers.", papers.size());
                return papers;
            }
        }
        papers = new ArrayList<>();
        
        LOGGER.debug("No cache. Getting items...");
        if (!year.isEmpty()) {            
            papers = getPapersFromApi(year, count);
        } else {
            Year thisYear = Year.now();

            // Convert to MT
            ExecutorService executor = Executors.newWorkStealingPool();
            List<Future<List<AcademicApiPaper>>> tasks = new ArrayList<>();
            
            for (int i = 2011; i < thisYear.getValue(); i++) {
                final int threadYear = i;
                Future<List<AcademicApiPaper>> getPapersTask = executor.submit(() -> {
                    for (int j = 0; j < 10; j++) {
                        try {
                            return getPapersFromApi(String.valueOf(threadYear), count);
                        } catch (WebApplicationException webEx) {
                            LOGGER.debug("Request failed, trying again (" + (j + 1) + ")");
                        } 

                        TimeUnit.MILLISECONDS.sleep(j * 150);
                    }                    

                    LOGGER.error("Could not get response after multiple trials");
                    return null;    
                });
                
                tasks.add(getPapersTask);
            }
            
            for (Future<List<AcademicApiPaper>> task : tasks) {
                try {                
                    List<AcademicApiPaper> papersFromThread = task.get();
                    if (papersFromThread != null) {
                        papers.addAll(papersFromThread);
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    LOGGER.error(ex.getMessage());
                } 
            }
            executor.shutdown();
        }

        cachedResponse = new ApiResourceResponse(papers);
        appResources.addToCache(request, cachedResponse);
        LOGGER.info("Got ids for {} new papers.", papers.size());
        return papers;
    }

    private List<AcademicApiPaper> getPapersFromApi(String year, int count) throws IOException, WebApplicationException {
        LOGGER.info("Getting papers for year {}", year);
        List<AcademicApiPaper> papers = new ArrayList<>();

        papers.addAll(getPapersFromApiByExtendedProps(year, count));
        papers.addAll(getPapersFromApiByConferenceName(year, count));

        LOGGER.info("{} papers were retrieved in total.", papers.size());
        return papers;
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
                        .filter(paper
                                -> paper.getExtendedProperties().getBv().toUpperCase().contains(EXPECTED_BV_VALUE)
                        );

        papers = filteredPapers.collect(Collectors.toList());
        LOGGER.debug("{} papers were found with '{}' value in the BV field", papers.size(), EXPECTED_BV_VALUE);

        return papers;
    }

    private String queryTheAcademicApi(List<AbstractMap.SimpleEntry<String, Object>> params) throws WebApplicationException {
        return queryTheAcademicApi(this.MS_COGNITIVE_API_TARGET, ACEDEMIC_API_EVALUATE_PATH, params);
    }

    private String queryTheAcademicApi(String webTarget, String webPath, List<AbstractMap.SimpleEntry<String, Object>> params) throws WebApplicationException {
        LOGGER.debug("Sending a query to the MS Academic API.");

        ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);

        WebTarget target = client.target(webTarget);
        target = target.path(webPath);
        for (AbstractMap.SimpleEntry<String, Object> param : params) {
            target = target.queryParam(param.getKey(), param.getValue().toString());
        }
        target = target.queryParam("model", "latest");

        LOGGER.debug("Target: {}, {}", target.toString(), params.toString());
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("Ocp-Apim-Subscription-Key", ACADEMIC_API_SUBSCRIPTION_KEY);

        Response response = invocationBuilder.get();
        int status = response.getStatus();
        LOGGER.debug("Response status: {} - {}", String.valueOf(status), response.getStatusInfo().getReasonPhrase());
        if (status < 200 || status > 300) {
            String errorOutput = response.readEntity(String.class);
            LOGGER.error(response.getStatusInfo().getReasonPhrase() + " - " + errorOutput);
            throw new WebApplicationException(response.getStatusInfo().getReasonPhrase());
        }

        LOGGER.debug("Entity was sucessfully retrieved from the API.");
        return response.readEntity(String.class);
    }

    private String queryTheAcademicApi(String conferenceName, String year, int count) {

        String expr = "Composite(C.CN='" + conferenceName + "')";
        if (year != null && !year.isEmpty()) {
            expr = "And(" + expr + ", Y=" + year + ")";
        }

        List<AbstractMap.SimpleEntry<String, Object>> params = new ArrayList<>();
        params.add(new AbstractMap.SimpleEntry<>("expr", expr));
        params.add(new AbstractMap.SimpleEntry<>("attributes", "Ti,Id,Y,E"));
        params.add(new AbstractMap.SimpleEntry<>("Count", count));

        return queryTheAcademicApi(params);
    }

}
