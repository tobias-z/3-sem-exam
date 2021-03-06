package entities.project;

import entities.projecthours.ProjectUserHours;
import entities.user.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "project")
@NamedQueries({
    @NamedQuery(name = "Project.deleteAllRows", query = "DELETE from Project")
})
public class Project implements Serializable {

    private static final long serialVersionUID = -9023989040895983890L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(
        mappedBy = "project",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @JoinColumn(name = "users")
    private List<ProjectUserHours> projectUserHours;

    public Project() {
    }

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
        this.projectUserHours = new ArrayList<>();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
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

    public List<ProjectUserHours> getProjectUserHours() {
        return projectUserHours;
    }

    public void setProjectUserHours(List<ProjectUserHours> projectUserHours) {
        this.projectUserHours = projectUserHours;
    }
}
