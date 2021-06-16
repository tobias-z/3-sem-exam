package dtos.user;


import entities.user.User;
import java.util.List;
import java.util.stream.Collectors;

public class DeveloperDTO {

    private String name;
    private String email;
    private String phone;
    private Integer billingPerHour;

    public DeveloperDTO(String name, String email, String phone, Integer billingPerHour) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.billingPerHour = billingPerHour;
    }

    public DeveloperDTO(User user) {
        this.name = user.getUserName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.billingPerHour = user.getBillingPerHour();
    }

    public static List<DeveloperDTO> getDevelopersFromUsers(List<User> users) {
        return users.stream()
            .map(user -> new DeveloperDTO(user))
            .collect(Collectors.toList());
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
}
