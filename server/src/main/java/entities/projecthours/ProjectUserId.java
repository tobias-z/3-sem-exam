package entities.projecthours;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class ProjectUserId implements Serializable {

    private static final long serialVersionUID = 8198718930567675169L;

    private String username;
    private Integer projectId;

    public ProjectUserId(String username, Integer projectId) {
        this.username = username;
        this.projectId = projectId;
    }

    public ProjectUserId() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectUserId)) {
            return false;
        }
        ProjectUserId that = (ProjectUserId) o;
        return Objects.equals(getUsername(), that.getUsername()) && Objects
            .equals(getProjectId(), that.getProjectId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getProjectId());
    }
}
