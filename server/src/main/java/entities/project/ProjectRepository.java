package entities.project;

import dtos.project.ProjectDTO;
import dtos.project.ProjectsDTO;
import java.util.List;
import javax.ws.rs.WebApplicationException;

public interface ProjectRepository {

    ProjectsDTO getAllProjects() throws WebApplicationException;

    ProjectDTO createProject(ProjectDTO projectDTO) throws WebApplicationException;

}
