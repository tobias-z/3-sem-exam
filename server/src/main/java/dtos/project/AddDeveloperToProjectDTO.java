package dtos.project;

public class AddDeveloperToProjectDTO {

    private String userStory;
    private String description;

    public AddDeveloperToProjectDTO(String userStory, String description) {
        this.userStory = userStory;
        this.description = description;
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
