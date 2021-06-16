package dtos.project;

import entities.projecthours.ProjectUserHours;
import entities.user.User;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectUserHoursDTO {

    private Integer hoursSpent;
    private String userStory;
    private String description;
    private String name;
    private String email;
    private String phone;
    private Integer billingPerHour;
    private boolean isComplete;

    public ProjectUserHoursDTO(Integer hoursSpent, String userStory, String description, String name,
        String email, String phone, Integer billingPerHour, boolean isComplete) {
        this.hoursSpent = hoursSpent;
        this.userStory = userStory;
        this.description = description;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.billingPerHour = billingPerHour;
        this.isComplete = isComplete;
    }

    public ProjectUserHoursDTO(ProjectUserHours projectUserHours) {
        this.hoursSpent = projectUserHours.getHoursSpent();
        this.userStory = projectUserHours.getUserStory();
        this.description = projectUserHours.getDescription();
        this.name = projectUserHours.getUser().getUserName();
        this.email = projectUserHours.getUser().getEmail();
        this.phone = projectUserHours.getUser().getPhone();
        this.billingPerHour = projectUserHours.getUser().getBillingPerHour();
        this.isComplete = projectUserHours.isComplete();
    }

    public static List<ProjectUserHoursDTO> getProjectUserHoursDTOSFromProjectUserHours(
        List<ProjectUserHours> projectUserHours) {
        return projectUserHours.stream()
            .map(p -> new ProjectUserHoursDTO(p))
            .collect(Collectors.toList());
    }

    public static List<ProjectUserHoursDTO> getProjectUSerHoursDTOSFromUser(
        User user) {
        return getProjectUserHoursDTOSFromProjectUserHours(user.getProjectUserHours());
    }

    public Integer getHoursSpent() {
        return hoursSpent;
    }

    public void setHoursSpent(Integer hoursSpent) {
        this.hoursSpent = hoursSpent;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
