package facades;

import dtos.user.DeveloperDTO;
import dtos.user.UserDTO;
import entities.user.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.WebApplicationException;
import security.errorhandling.AuthenticationException;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public List<DeveloperDTO> getAllDevelopers() {
        EntityManager em = emf.createEntityManager();
        try {
            List<User> users = em.createQuery(
                "SELECT u FROM User u JOIN u.roleList r WHERE r.roleName = :roleName", User.class
            ).setParameter("roleName", "user").getResultList();
            return DeveloperDTO.getDevelopersFromUsers(users);
        } catch (Exception e) {
            throw new WebApplicationException("Unable to find any developers");
        } finally {
            em.close();
        }
    }

    public UserDTO getVerifiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return new UserDTO(user);
    }

}
