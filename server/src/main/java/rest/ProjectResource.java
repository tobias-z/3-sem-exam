package rest;

import dtos.project.ProjectDTO;
import dtos.project.ProjectsDTO;
import entities.project.ProjectRepository;
import facades.ProjectFacade;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import rest.provider.Provider;
import utils.Populate;

@Path("projects")
public class ProjectResource extends Provider {

    private final ProjectRepository REPO = ProjectFacade.getInstance(EMF);

    @GET
    public Response getAllProjects() {
        ProjectsDTO projects = REPO.getAllProjects();
        return Response.ok(GSON.toJson(projects)).build();
    }

    @GET
    @Path("/populate")
    public Response populate() {
        Populate.populate();
        return Response.ok().build();
    }
}