package facades;

import dtos.project.ProjectDTO;
import dtos.project.ProjectsDTO;
import entities.project.Project;
import entities.project.ProjectRepository;
import entities.user.User;
import entities.user.UserAction;
import java.util.List;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.WebApplicationException;

public class ProjectFacade implements ProjectRepository {

    private static ProjectFacade instance;
    private static EntityManagerFactory emf;

    private ProjectFacade() {
    }

    public static ProjectFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ProjectFacade();
        }
        return instance;
    }

    private void executeInsideTransaction(String errorMessage, Consumer<EntityManager> consumer) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            consumer.accept(em);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new WebApplicationException(errorMessage);
        } finally {
            em.close();
        }
    }

    private <T> T withUser(String username, UserAction<T> action) {
        EntityManager em = emf.createEntityManager();
        try {
            User user = em.find(User.class, username);
            if (user == null) {
                throw new WebApplicationException("No user found with username: " + username);
            }
            return action.commit(user, em);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public ProjectsDTO getAllProjects() throws WebApplicationException {
        EntityManager em = emf.createEntityManager();
        try {
            List<Project> projects = em.createQuery("SELECT p FROM Project p", Project.class).getResultList();
            return new ProjectsDTO(projects);
        } catch (Exception e) {
            throw new WebApplicationException("Unable to find any projects");
        } finally {
            em.close();
        }
    }

    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) throws WebApplicationException {
        Project project = new Project(projectDTO.getName(), projectDTO.getDescription());
        executeInsideTransaction("Unable to create project", em -> em.persist(project));
        return new ProjectDTO(project);
    }

}