package entities.project;

import dtos.project.ProjectDTO;
import java.util.List;
import javax.ws.rs.WebApplicationException;

public interface ProjectRepository {

    List<ProjectDTO> getAllProjects() throws WebApplicationException;

}
