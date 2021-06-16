package dtos.project;

import entities.project.Project;
import java.util.List;

public class ProjectsDTO {

    private List<ProjectDTO> projects;

    public ProjectsDTO(List<Project> projects) {
        this.projects = ProjectDTO.getProjectDTOSFromProjects(projects);
    }

    public List<ProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDTO> projects) {
        this.projects = projects;
    }
}
