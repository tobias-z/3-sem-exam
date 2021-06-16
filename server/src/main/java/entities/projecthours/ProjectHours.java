package entities.projecthours;

import entities.project.Project;
import entities.user.User;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Table
@Entity
public class ProjectHours implements Serializable {

    private static final long serialVersionUID = 5296883145841368340L;

    @EmbeddedId
    private ProjectUserId id;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "projectId")
    private Project project;

    @ManyToOne
    @MapsId("username")
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "hoursSpendt")
    private Integer hoursSpendt;

    @Column(name = "userStory")
    private String userStory;

    @Column(name = "description")
    private String description;

    public ProjectHours(Project project, User user, Integer hoursSpendt, String userStory,
        String description) {
        this.id = new ProjectUserId(user.getUserName(), project.getId());
        this.project = project;
        this.user = user;
        this.hoursSpendt = hoursSpendt;
        this.userStory = userStory;
        this.description = description;
    }

    public ProjectHours() {
    }

    public ProjectUserId getId() {
        return id;
    }

    public void setId(ProjectUserId id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getHoursSpendt() {
        return hoursSpendt;
    }

    public void setHoursSpendt(Integer hoursSpendt) {
        this.hoursSpendt = hoursSpendt;
    }

    public String getUserStory() {
        return userStory;
    }

    public void setUserStory(String userStory) {
        this.userStory = userStory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
