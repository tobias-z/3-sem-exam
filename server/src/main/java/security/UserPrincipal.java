package security;

import dtos.user.RoleDTO;
import entities.user.User;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserPrincipal implements Principal {

  private String username;
  private List<String> roles = new ArrayList<>();
  private List<RoleDTO> roleDTOS;

  /* Create a UserPrincipal, given the Entity class User*/
  public UserPrincipal(User user) {
    this.username = user.getUserName();
    this.roles = user.getRolesAsStrings();
  }

  public UserPrincipal(String username, String[] roles, List<RoleDTO> roleDTOS) {
    super();
    this.username = username;
    this.roles = Arrays.asList(roles);
    this.roleDTOS = roleDTOS;
  }

  public List<RoleDTO> getRoleDTOS() {
    return roleDTOS;
  }

  @Override
  public String getName() {
    return username;
  }

  public boolean isUserInRole(String role) {
    return this.roles.contains(role);
  }
}