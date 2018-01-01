package com.idog.vis.academicvisapi.resources;

import com.idog.vis.academicvisapi.VisServerAppResources;
import com.idog.vis.academicvisapi.VisServerRequestResources;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/campaign")
public class CampaignResource {
    
    @Inject
    private VisServerRequestResources requestResources;
    
    @Inject 
    private VisServerAppResources appResources;
    
    /*
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getCampaigns() throws URISyntaxException, Exception {        
        // CLIENT //
        ////////////        
        ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);
        WebTarget webTarget = client.target("");
        WebTarget resourceWebTarget = webTarget.path("");
        
        
        Invocation.Builder invocationBuilder = resourceWebTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("Authorization", "");
        
        return invocationBuilder.get();
        
    }*/
    
    @Path("/")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getCampaigns() {
        
        return Response.ok().entity("foo").build();
    }
    
}
