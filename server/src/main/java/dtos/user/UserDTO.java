package dtos.user;

import entities.user.User;
import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    private String username;
    private List<RoleDTO> roles;

    public UserDTO(String username, List<RoleDTO> roles) {
        this.username = username;
        this.roles = roles;
    }

    public UserDTO(User user) {
        this.username = user.getUserName();
        this.roles = RoleDTO.getRoleDTOSFromRoles(user.getRoleList());
    }

    public List<String> getRolesAsStrings() {
        if (roles.isEmpty()) {
            return null;
        }
        List<String> rolesAsStrings = new ArrayList<>();
        roles.forEach((role) -> {
            rolesAsStrings.add(role.getName());
        });
        return rolesAsStrings;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }
}
