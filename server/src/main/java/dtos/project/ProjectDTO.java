package dtos.project;

import entities.project.Project;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectDTO {

    private Integer id;
    private String name;
    private String description;

    public ProjectDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ProjectDTO(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.description = project.getDescription();
    }

    public static List<ProjectDTO> getProjectDTOSFromProjects(List<Project> projects) {
        return projects.stream()
            .map(project -> new ProjectDTO(project))
            .collect(Collectors.toList());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
