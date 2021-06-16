package facades;

import dtos.project.ProjectDTO;
import dtos.project.ProjectsDTO;
import entities.project.Project;
import entities.project.ProjectRepository;
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
}