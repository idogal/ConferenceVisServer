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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

/**
 *
 * @author idoga
 */
@Path("/demo")
public class ApiResource {

    @Inject
    private VisServerRequestResources requestResources;

    @Inject
    private VisServerAppResources appResources;

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChasePapers(
            @DefaultValue("1000") @QueryParam("Count") int count,
            @DefaultValue("") @QueryParam("Year") String year) throws IOException {

        List<AcademicApiPaper> papers = null;
        if (!year.isEmpty()) {
            papers = getPapersFromApi(year, count);
        } else {
            Year thisYear = Year.now();

            for (int i = 2011; i < thisYear.getValue(); i++) {
                papers = getPapersFromApi(String.valueOf(i), count);
            }
        }

        return Response.ok().entity(papers).build();
    }

    private List<AcademicApiPaper> getPapersFromApi(String year, int count) throws IOException {
        List<AcademicApiPaper> papers = new ArrayList<>();
        
        papers.addAll(getPapersFromApiByExtendedProps(year, count));
        papers.addAll(getPapersFromApiByConferenceName(year, count));
        
        return papers;
    }
    
    private String queryTheAcademicApi(String conferenceName, String year, int count) throws IOException {
        ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);

        String expr = "Composite(C.CN='" + conferenceName + "')";
        if (year != null && !year.isEmpty()) {
            expr = "And(" + expr + ", Y=" + year + ")";
        }

        WebTarget webTarget = client.target("https://westus.api.cognitive.microsoft.com");
        webTarget = webTarget.path("academic/v1.0/evaluate");
        webTarget = webTarget.queryParam("expr", expr);
        webTarget = webTarget.queryParam("attributes", "Ti,Id,Y,E");
        webTarget = webTarget.queryParam("Count", count);

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("Ocp-Apim-Subscription-Key", "e1878999137d481089b706561bd0f5be");

        Response response = invocationBuilder.get();
        int status = response.getStatus();
        if (status < 200 || status > 300) {
            throw new WebApplicationException(response.getStatusInfo().getReasonPhrase());
        }

        return response.readEntity(String.class);
    }
    
    private List<AcademicApiPaper> getPapersFromApiByConferenceName(String year, int count) throws IOException {
        String entityString = queryTheAcademicApi("chase", year, count);

        ObjectMapper mapper = appResources.getMapper();
        AcademicApiResponse readValue = mapper.readValue(entityString, AcademicApiResponse.class);

        return readValue.entities;
    }
    
    private List<AcademicApiPaper> getPapersFromApiByExtendedProps(String year, int count) throws IOException {

        String entityString = queryTheAcademicApi("icse", year, count);

        ObjectMapper mapper = appResources.getMapper();
        AcademicApiResponse readValue = mapper.readValue(entityString, AcademicApiResponse.class);

        List<AcademicApiPaper> papers = readValue.entities;
        Stream<AcademicApiPaper> filteredPapers
                = papers.stream()
                        .filter(paper -> paper.getExtendedProperties() != null)
                        .filter(paper -> 
                                paper.getExtendedProperties().getBv().toUpperCase().contains("WORKSHOP ON COOPERATIVE AND HUMAN ASPECTS OF SOFTWARE ENGINEERING")
                );
        
        papers = filteredPapers.collect(Collectors.toList());

        return papers;
    }
}
