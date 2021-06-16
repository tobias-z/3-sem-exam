package facades;

import entities.projecthours.ProjectUserHours;
import entities.projecthours.ProjectUserHoursRepository;
import entities.projecthours.ProjectUserId;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.WebApplicationException;

public class ProjectUserHoursFacade implements ProjectUserHoursRepository {

    private static ProjectUserHoursFacade instance;
    private static EntityManagerFactory emf;

    private ProjectUserHoursFacade() {
    }

    public static ProjectUserHoursFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ProjectUserHoursFacade();
        }
        return instance;
    }

    @Override
    public void editProjectUserHours(String username, Integer id, Integer hoursWorked)
        throws WebApplicationException {
        EntityManager em = emf.createEntityManager();
        try {
            ProjectUserHours projectUserHours = em.find(ProjectUserHours.class, new ProjectUserId(username, id));
            if (projectUserHours == null) {
                throw new WebApplicationException(
                    "Unable to find project user hours for username: " + username + ", and project id: " + id
                );
            }
            em.getTransaction().begin();
            projectUserHours.setHoursSpent(projectUserHours.getHoursSpent() + hoursWorked);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new WebApplicationException("Unable to edit project user hours");
        } finally {
            em.close();
        }
    }
}