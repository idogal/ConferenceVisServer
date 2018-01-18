/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources;

import com.idog.vis.academicvisapi.VisServerAppResources;
import com.idog.vis.academicvisapi.beans.AcademicApiPaper;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

    @Path("nodes")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNodes() {
        LOGGER.info("Request recieved: {} {}", servletRequest.getRequestURI(), servletRequest.getQueryString());
        
        VisMsApiService msApiService = new VisMsApiService(appResources);
        try {
            List<AcademicApiPaper> chasePapers = msApiService.getChasePapersAsList("", false);
            processPapersList(chasePapers);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
        }

        return Response.ok().build();
    }

    public void processPapersList(List<AcademicApiPaper> chasePapers) {
        System.out.println("");
    }
}
