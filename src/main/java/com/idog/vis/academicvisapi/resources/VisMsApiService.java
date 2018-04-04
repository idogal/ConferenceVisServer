/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idog.vis.academicvisapi.VisServerAppResources;
import com.idog.vis.academicvisapi.beans.AcademicApiPaper;
import com.idog.vis.academicvisapi.beans.AcademicApiResponse;
import com.idog.vis.academicvisapi.utility.VisPersistence;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;

/**
 *
 * @author idoga
 */
public class VisMsApiService implements MsApiService {

    private static final Logger LOGGER = LogManager.getLogger("VisApi");
    private final String MS_COGNITIVE_API_TARGET = "https://westus.api.cognitive.microsoft.com";
    private final String ACEDEMIC_API_EVALUATE_PATH = "academic/v1.0/evaluate";
    private final String ACADEMIC_API_SUBSCRIPTION_KEY = "e1878999137d481089b706561bd0f5be";
    private final String EXPECTED_BV_VALUE = "WORKSHOP ON COOPERATIVE AND HUMAN ASPECTS OF SOFTWARE ENGINEERING";
    private final String EXPECTED_CN_VALUE = "chase";

    private final VisServerAppResources appResources;
    private final VisPersistence pService;

    public VisMsApiService(VisServerAppResources appResources) {
        this.appResources = appResources;
        this.pService = appResources.getVisPersistenceService();
    }

    @Override
    public List<AcademicApiPaper> getChasePapersAsList(String year, boolean noCache) throws IOException {
        List<AcademicApiPaper> allPapers = new ArrayList<>();
        List<AcademicApiPaper> papersOfChase = getPapersOfChaseConference(1000, year, noCache);
        allPapers = getPapersDetails(papersOfChase, noCache);
        return allPapers;
    }    
    
    /**
     * Get full paper details from the API, by the ID of the input paper list.
     * <br>Requests are sent in a multi-threaded manner, in multiple attempts,
     * if a 429 result occurs.
     *
     * @param chasePapersIds A List of academic API papers
     * @return A List of the corresponding academic API papers
     *
     */
    private List<AcademicApiPaper> getPapersDetails(List<AcademicApiPaper> chasePapersIds, boolean noCache) {
        LOGGER.info("Trying to get the full details of the input papers list");
        List<AcademicApiPaper> allPapers = new ArrayList<>();
        int startFrom = 0;
        int batchSize = 10;
        boolean papersRemaining = true;
        while (papersRemaining) {
            int currentFinishPosition = startFrom + batchSize - 1;
            if (currentFinishPosition >= chasePapersIds.size() - 1) {
                papersRemaining = false;
                currentFinishPosition = chasePapersIds.size() - 1;
            }
            // Process current batch
            int poolSize = currentFinishPosition - startFrom + 1;
            ExecutorService executor = Executors.newFixedThreadPool(poolSize);
            List<Future<List<AcademicApiPaper>>> tasks = new ArrayList<>();
            for (int i = startFrom; i <= currentFinishPosition; i++) {
                AcademicApiPaper chasePaper = chasePapersIds.get(i);
                // Create thread
                Future<List<AcademicApiPaper>> getPapersTask = executor.submit(() -> {
                    int attempts = 0;
                    for (int j = 0; j < 10; j++) {
                        try {
                            long id = chasePaper.getId();
                            return getChasePaperById(String.valueOf(id), noCache);
                        } catch (WebApplicationException webEx) {
                            LOGGER.debug("Request failed, trying again (" + (j + 1) + ")");
                        } catch (IOException | ExecutionException ex) {
                            LOGGER.warn(ex.getMessage());
                        }
                        TimeUnit.MILLISECONDS.sleep((j + 1) * 200);
                        attempts = j;
                    }
                    LOGGER.error("Could not get response after {} attempts", attempts);
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
            try {
                executor.shutdown();
                executor.awaitTermination(60, TimeUnit.SECONDS);
            } catch (InterruptedException ex) {
                LOGGER.error(ex);
            } finally {
                if (!executor.isTerminated()) {
                    LOGGER.error("cancel non-finished tasks");
                }
                executor.shutdownNow();
            }
            startFrom = startFrom + batchSize;
        }
        LOGGER.info("Got {} papers", allPapers.size());
        return allPapers;
    }

    private List<AcademicApiPaper> getPapersFromApi(String year, int count) throws IOException, WebApplicationException {
        LOGGER.info("Getting papers for year {}", year);
        List<AcademicApiPaper> papers = new ArrayList<>();
        papers.addAll(getPapersFromApiByExtendedProps(year, count));
        papers.addAll(getPapersFromApiByConferenceName(year, count));
        LOGGER.info("{} papers were retrieved in total.", papers.size());
        return papers;
    }

    private List<AcademicApiPaper> getPapersOfChaseConference(int count, String year, boolean noCache) throws IOException {
        LOGGER.info("Trying getting papers ids list...");
        
        ApiResourceRequest request = new ApiResourceRequest(year, count);
        List<AcademicApiPaper> papers;
        ApiResourceResponse cachedResponse = null;
//        if (!noCache) {
//            this.appResources.
//            cachedResponse = this.appResources.getChasePaperFromCache(request);
//            papers = (cachedResponse == null) ? null : cachedResponse.getPapers();
//            if (papers != null && !papers.isEmpty()) {
//                LOGGER.info("Got ids for {} cached papers.", papers.size());
//                return papers;
//            }
//        }
        
        papers = new ArrayList<>();
        LOGGER.debug("No cache. Getting items...");
        if (!year.isEmpty()) {
            papers = getPapersFromApi(year, count);
        } else {
            Year thisYear = Year.now();
            // Convert to MT
            ExecutorService executor = Executors.newWorkStealingPool();
            List<Future<List<AcademicApiPaper>>> tasks = new ArrayList<>();
            for (int i = 2010; i < thisYear.getValue(); i++) {
                final int threadYear = i;
                Future<List<AcademicApiPaper>> getPapersTask = executor.submit(() -> {
                    for (int j = 0; j < 10; j++) {
                        try {
                            return getPapersFromApi(String.valueOf(threadYear), count);
                        } catch (WebApplicationException webEx) {
                            LOGGER.debug("Request failed, trying again (" + (j + 1) + ")");
                        }
                        TimeUnit.MILLISECONDS.sleep((j + 1) * 200);
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
            try {
                executor.shutdown();
                executor.awaitTermination(60, TimeUnit.SECONDS);
            } catch (InterruptedException ex) {
                LOGGER.error(ex);
            } finally {
                if (!executor.isTerminated()) {
                    LOGGER.error("cancel non-finished tasks");
                }
                executor.shutdownNow();
            }
        }
        
        cachedResponse = new ApiResourceResponse(papers);
        //this.appResources.addChasePapersToCache(request, cachedResponse);
        LOGGER.info("Got ids for {} new papers.", papers.size());
        return papers;
    }

    /**
     * Build a request for the academic api, for a single paper, by an ID, and
     * gets a broad list of attributes.
     *
     * @param id Academic API Id
     * @return A list of papers matching this ID (probably just 1)
     * @throws IOException
     * @throws ExecutionException
     * @throws WebApplicationException
     */
    List<AcademicApiPaper> getChasePaperById(String id, boolean noCache) throws IOException, ExecutionException, WebApplicationException {
        LOGGER.info("Building a request by an ID for: {}", id);
        AcademicApiResponse readValue; 
        
        // Try to get from cache
//        if (!noCache) {
//            readValue = this.appResources.getPaperByIdFromCache(id);
//            if (readValue != null) {
//                LOGGER.info("Got data for id {} from cache", id);
//                return readValue.entities;
//            }
//        }        
        
        // Send a query to the API if there is no cache
        String expr = "Id=" + id;
        String attributes = "Id,Ti,AA.AuN,AA.AuId,AA.AfN,AA.AfId,AA.S,F.FN,F.FId,RId,W";
        List<AbstractMap.SimpleEntry<String, Object>> params = new ArrayList<>();
        params.add(new AbstractMap.SimpleEntry<>("expr", expr));
        params.add(new AbstractMap.SimpleEntry<>("attributes", attributes));
        String entityJson = pService.getMsApiResponse(id);
        if (entityJson.isEmpty()) {
            entityJson = queryTheAcademicApi(params);
            pService.storeMsApiResponse(id, entityJson);
        }         
        ObjectMapper mapper = this.appResources.getMapper();
        readValue = mapper.readValue(entityJson, AcademicApiResponse.class);        
//        this.appResources.addPaperByIdToCache(id, readValue);
        
        LOGGER.debug("Response was serialised into an AcademicApiResponse successfully");
        if (readValue.entities.isEmpty()) {
            LOGGER.warn("{} papers were found with '{}' id", readValue.entities.size(), id);
        } else {
            LOGGER.info("{} papers were found with '{}' id", readValue.entities.size(), id);
        }
        return readValue.entities;
    }

    private List<AcademicApiPaper> getPapersFromApiByConferenceName(String year, int count) throws IOException {
        LOGGER.debug("Trying to get papers by the C.CN property.");
        
        String entityString = pService.getMsApiResponse(EXPECTED_CN_VALUE, year, count);
        if (entityString.isEmpty()) {
            entityString = queryTheAcademicApi(EXPECTED_CN_VALUE, year, count);
            pService.storeMsApiResponse(EXPECTED_CN_VALUE, year, count, entityString);
        }        
        
        ObjectMapper mapper = appResources.getMapper();
        AcademicApiResponse readValue = mapper.readValue(entityString, AcademicApiResponse.class);
        LOGGER.debug("Response was serialised into an AcademicApiResponse successfully");
        LOGGER.debug("{} papers were found with '{}' value in the C.CN field", readValue.entities.size(), EXPECTED_CN_VALUE);
        return readValue.entities;
    }
    
    private List<AcademicApiPaper> getPapersFromApiByExtendedProps(String year, int count) throws IOException {
        LOGGER.debug("Trying to get papers by the Extended properties.");
        
        String entityString = pService.getMsApiResponse("icse", year, count);
        if (entityString.isEmpty()) {
            entityString = queryTheAcademicApi("icse", year, count);
            pService.storeMsApiResponse("icse", year, count, entityString);
        }            
        
        ObjectMapper mapper = this.appResources.getMapper();
        AcademicApiResponse readValue = mapper.readValue(entityString, AcademicApiResponse.class);
        LOGGER.debug("Response was serialised into an AcademicApiResponse successfully");
        List<AcademicApiPaper> papers = readValue.entities;
        Stream<AcademicApiPaper> filteredPapers = 
                papers.stream()
                        .filter((AcademicApiPaper paper) -> paper.getExtendedProperties() != null)
                        .filter((AcademicApiPaper paper) -> paper.getExtendedProperties().getBv().toUpperCase().contains(EXPECTED_BV_VALUE));
        
        papers = filteredPapers.collect(Collectors.toList());
        LOGGER.debug("{} papers were found with '{}' value in the BV field", papers.size(), EXPECTED_BV_VALUE);
        return papers;
    }    

    private String queryTheAcademicApi(List<AbstractMap.SimpleEntry<String, Object>> params) throws WebApplicationException {
        return queryTheAcademicApi(MS_COGNITIVE_API_TARGET, ACEDEMIC_API_EVALUATE_PATH, params);
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
            if (status == 429) {
                LOGGER.warn(response.getStatusInfo().getReasonPhrase() + " - " + errorOutput);
                throw new WebApplicationException(response.getStatusInfo().getReasonPhrase());
            } else {
                LOGGER.error(response.getStatusInfo().getReasonPhrase() + " - " + errorOutput);
                throw new WebApplicationException(response.getStatusInfo().getReasonPhrase());
            }
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
