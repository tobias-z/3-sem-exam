package facades;

import dtos.project.ProjectDTO;
import dtos.project.ProjectsDTO;
import dtos.user.DeveloperDTO;
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
                throw new WebApplicationException("No user found with username: " + username, 400);
            }
            return action.commit(user, em);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(), 400);
        } finally {
            em.close();
        }
    }

    private boolean isValidProjectDTO(ProjectDTO project) {
        if (project.getName() == null || project.getName().isEmpty()) {
            return false;
        } else if (project.getDescription() == null || project.getDescription().isEmpty()) {
            return false;
        }
        return true;
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
        if (!isValidProjectDTO(projectDTO)) {
            throw new WebApplicationException("Invalid project", 400);
        }
        Project project = new Project(projectDTO.getName(), projectDTO.getDescription());
        executeInsideTransaction("Unable to create project", em -> em.persist(project));
        return new ProjectDTO(project);
    }

    @Override
    public ProjectDTO addDeveloperToProject(String username, Integer projectId)
        throws WebApplicationException {
        return withUser(username, (user, em) -> {
            Project project = em.find(Project.class, projectId);
            if (project == null) {
                throw new WebApplicationException("Unable to find project with id: " + projectId, 400);
            }
            if (project.getUsers().contains(user)) {
                throw new WebApplicationException("That developer is already on this project", 400);
            }
            em.getTransaction().begin();
            user.addProject(project);
            em.getTransaction().commit();
            return new ProjectDTO(project);
        });
    }

}