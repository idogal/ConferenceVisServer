/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources;

import com.idog.vis.academicvisapi.VisServerAppResources;
import com.idog.vis.academicvisapi.VisServerRequestResources;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
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
    @Produces(MediaType.TEXT_PLAIN)
    public Response getDemo() {

        ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);
    
        WebTarget webTarget = client.target("https://westus.api.cognitive.microsoft.com");
        webTarget = webTarget.path("academic/v1.0/evaluate");
        webTarget = webTarget.queryParam("expr", "And(Composite(C.CN='icse'), Y=2015)");
        webTarget = webTarget.queryParam("attributes", "Ti,Id,Y,E");
        webTarget = webTarget.queryParam("Count", "1");
        
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("Ocp-Apim-Subscription-Key", "edd1731c7e5d48a1ac3f057a41726bfd");
        
        Response response = invocationBuilder.get();
        String readEntity = response.readEntity(String.class);
        
        //unmarshal JSON...
        
        return response;
    }

}
