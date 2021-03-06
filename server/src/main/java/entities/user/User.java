package entities.user;

import entities.project.Project;
import entities.projecthours.ProjectUserHours;
import entities.user.Role;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_name", length = 25)
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "user_pass")
    private String hashPassword;
    @JoinTable(name = "user_roles", joinColumns = {
        @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
        @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
    @ManyToMany
    private List<Role> roleList = new ArrayList<>();

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "billingPerHour")
    private Integer billingPerHour;

    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<ProjectUserHours> projectUserHours;

    public void addProject(Project project, Integer hoursSpent, String userStory,
        String description) {
        if (project != null) {
            ProjectUserHours projectUserHours = new ProjectUserHours(project, this, hoursSpent, userStory,
                description, false);
            this.projectUserHours.add(projectUserHours);
            project.getProjectUserHours().add(projectUserHours);
        }
    }

    public List<String> getRolesAsStrings() {
        if (roleList.isEmpty()) {
            return null;
        }
        List<String> rolesAsStrings = new ArrayList<>();
        roleList.forEach((role) -> {
            rolesAsStrings.add(role.getRoleName());
        });
        return rolesAsStrings;
    }

    public User() {
    }

    public User(String userName, String userPass) {
        this.userName = userName;
        this.hashPassword = BCrypt.hashpw(userPass, BCrypt.gensalt());
        this.projectUserHours = new ArrayList<>();
    }

    public User(String userName, String email, String phone, Integer billingPerHour) {
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.billingPerHour = billingPerHour;
        this.projectUserHours = new ArrayList<>();
    }

    //TODO Change when password is hashed
    public boolean verifyPassword(String pw) {
        return BCrypt.checkpw(pw, hashPassword);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return this.hashPassword;
    }

    public void setUserPass(String userPass) {
        this.hashPassword = BCrypt.hashpw(userPass, BCrypt.gensalt());
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public void addRole(Role userRole) {
        roleList.add(userRole);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getBillingPerHour() {
        return billingPerHour;
    }

    public void setBillingPerHour(Integer billingPerHour) {
        this.billingPerHour = billingPerHour;
    }

    public List<ProjectUserHours> getProjectUserHours() {
        return projectUserHours;
    }
}
