package entities.project;

import dtos.project.AddDeveloperToProjectDTO;
import dtos.project.ProjectDTO;
import dtos.project.ProjectsDTO;
import javax.ws.rs.WebApplicationException;

public interface ProjectRepository {

    ProjectsDTO getAllProjects() throws WebApplicationException;

    ProjectDTO createProject(ProjectDTO projectDTO) throws WebApplicationException;

    ProjectDTO addDeveloperToProject(
        String username,
        Integer projectId,
        AddDeveloperToProjectDTO addDeveloperToProjectDTO
    ) throws WebApplicationException;

    ProjectsDTO getAllDevelopersProjects(String username) throws WebApplicationException;

}
