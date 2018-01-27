/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources;

import com.idog.vis.academicvisapi.VisServerAppResources;
import com.idog.vis.academicvisapi.VisServerRequestResources;
import com.idog.vis.academicvisapi.beans.AcademicApiPaper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author idoga
 * @see
 * <a href="https://docs.microsoft.com/en-us/azure/cognitive-services/academic-knowledge/home"></a>
 */
@Path("/msapi")
public class ApiResource {

    @Inject
    private VisServerRequestResources requestResources;
    @Inject
    private VisServerAppResources appResources;
    @Context
    ServletContext servletContext;
    @Context
    HttpServletRequest servletRequest;

    private static final Logger LOGGER = LogManager.getLogger("VisApi");
    
    /**
     * Get details of paper entities by an id
     * <br><br> Sample request: http://localhost:8097/VisAPI/msapi/papers/2022897498
     * @param id The academic API id of the paper
     * @return
     * @throws IOException
     */
    @Path("papers/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChasePapers(
            @DefaultValue("") @PathParam("id") String id) throws IOException {

        LOGGER.info("Request recieved: {} {}", servletRequest.getRequestURI(), servletRequest.getQueryString());
        VisMsApiService msApiService = new VisMsApiService(appResources);
        
        if (id.isEmpty()) {
            LOGGER.warn("id parameter must be passed");
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"id parameter must be passed\"}").build();
        }

        List<AcademicApiPaper> chasePapers;
        try {
            chasePapers = msApiService.getChasePaperById(id, false);
        } catch (ExecutionException ex) {
            LOGGER.error(ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        LOGGER.info("Responding...");
        return Response.ok().entity(chasePapers).build();
    }

    /**
     * Get details of <b>CHASE</b> papers entities by the year (or no year, for
     * all years)
     * <br><br> Sample request: http://localhost:8097/VisAPI/msapi/papers?Year=2011
     * @param year Papers from a specific conference year
     * @param noCache True - for not using the cached results, false - for
     * cached results
     * @return
     */
    @Path("papers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChasePapers(
            @DefaultValue("") @QueryParam("Year") String year,
            @DefaultValue("false") @QueryParam("NoCache") boolean noCache) {

        LOGGER.info("Request recieved: {} {}", servletRequest.getRequestURI(), servletRequest.getQueryString());
        VisMsApiService msApiService = new VisMsApiService(appResources);
        
        List<AcademicApiPaper> allPapers = new ArrayList<>();

        try {
            allPapers = msApiService.getChasePapersAsList(year, noCache);
        } catch (IOException | RuntimeException ex) {
            LOGGER.error(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        LOGGER.info("Responding...");
        return Response.ok().entity(allPapers).build();
    }
}
