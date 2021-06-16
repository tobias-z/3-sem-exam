package rest;

import dtos.project.ProjectDTO;
import dtos.project.ProjectsDTO;
import entities.project.ProjectRepository;
import facades.ProjectFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
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

    @POST
    @RolesAllowed("admin")
    public Response createProject(String requestBody) {
        ProjectDTO projectDTO = GSON.fromJson(requestBody, ProjectDTO.class);
        ProjectDTO createdProject = REPO.createProject(projectDTO);
        return Response.ok(GSON.toJson(createdProject)).build();
    }

    @PUT
    @RolesAllowed("admin")
    public Response addDeveloperToProject(
        @QueryParam(value = "username") String username, @QueryParam("projectId") Integer projectId
    ) {
        ProjectDTO projectDTO = REPO.addDeveloperToProject(username, projectId);
        return Response.ok(GSON.toJson(projectDTO)).build();
    }

    @GET
    @Path("/populate")
    public Response populate() {
        Populate.populate();
        return Response.ok().build();
    }
}