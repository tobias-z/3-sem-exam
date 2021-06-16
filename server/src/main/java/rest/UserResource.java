package rest;

import dtos.user.DeveloperDTO;
import facades.UserFacade;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import rest.provider.Provider;

@Path("users")
public class UserResource extends Provider {

    private final UserFacade FACADE = UserFacade.getUserFacade(EMF);

    @GET
    public Response getAllDevelopers() {
        List<DeveloperDTO> developerDTOS = FACADE.getAllDevelopersFromProject();
        return Response.ok(GSON.toJson(developerDTOS)).build();
    }
}