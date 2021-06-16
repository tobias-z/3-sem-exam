package rest;

import dtos.project.ProjectUserHoursDTO;
import entities.projecthours.ProjectUserHours;
import entities.projecthours.ProjectUserHoursRepository;
import facades.ProjectUserHoursFacade;
import java.sql.PseudoColumnUsage;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import rest.provider.Provider;

@Path("project-user-hours")
public class ProjectUserHoursResource extends Provider {

    private final ProjectUserHoursRepository REPO = ProjectUserHoursFacade.getInstance(EMF);

    @Context
    SecurityContext context;

    @PUT
    @RolesAllowed({"user", "admin"})
    public Response editProjectUserHours(
        @QueryParam(value = "projectId") Integer projectId,
        @QueryParam(value = "hoursWorked") Integer hoursWorked
    ) {
        String username = context.getUserPrincipal().getName();
        ProjectUserHoursDTO projectUserHoursDTO = REPO.editProjectUserHours(username, projectId, hoursWorked);
        return Response.ok().build();
    }
}