package dtos.project;

import entities.project.Project;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProjectDTO {

    private Integer id;
    private String name;
    private String description;
    private List<ProjectUserHoursDTO> developers;

    public ProjectDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ProjectDTO(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.description = project.getDescription();
        this.developers = ProjectUserHoursDTO.getProjectUserHoursDTOSFromProjectUserHours(project.getProjectUserHours());
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

    public List<ProjectUserHoursDTO> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<ProjectUserHoursDTO> developers) {
        this.developers = developers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectDTO)) {
            return false;
        }
        ProjectDTO that = (ProjectDTO) o;
        return Objects.equals(getId(), that.getId()) && Objects
            .equals(getName(), that.getName()) && Objects
            .equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription());
    }
}
