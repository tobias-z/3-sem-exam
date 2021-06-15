package dtos.user;

import entities.Role;
import java.util.List;
import java.util.stream.Collectors;

public class RoleDTO {

    private String name;

    public RoleDTO(String name) {
        this.name = name;
    }

    public static List<RoleDTO> getRoleDTOSFromRoles(List<Role> roles) {
        return roles.stream().map(role -> new RoleDTO(role.getRoleName())).collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
