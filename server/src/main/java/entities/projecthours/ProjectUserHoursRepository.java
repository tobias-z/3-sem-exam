package entities.projecthours;

import dtos.project.ProjectUserHoursDTO;
import javax.ws.rs.WebApplicationException;

public interface ProjectUserHoursRepository {

    ProjectUserHoursDTO editProjectUserHours(String username, Integer id, Integer hoursWorked) throws WebApplicationException;

    ProjectUserHoursDTO completeProjectUserHours(String username, Integer projectId) throws WebApplicationException;

}
