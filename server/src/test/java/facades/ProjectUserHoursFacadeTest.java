package facades;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import dtos.project.ProjectUserHoursDTO;
import entities.project.Project;
import entities.projecthours.ProjectUserHoursRepository;
import entities.user.Role;
import entities.user.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.WebApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

class ProjectUserHoursFacadeTest {

    private static EntityManagerFactory emf;
    private static ProjectUserHoursRepository repo;

    public static Project project1;
    public static User user;

    @BeforeEach
    void setUp() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        repo = ProjectUserHoursFacade.getInstance(emf);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("ProjectUserHours.deleteAllRows").executeUpdate();
            em.createNamedQuery("Project.deleteAllRows").executeUpdate();
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();

            project1 = new Project("project1", "something1");
            em.persist(project1);

            Role userRole = new Role("user");
            user = new User("user", "test");
            user.addRole(userRole);
            user.addProject(project1, 0, "Insane", "Do it");
            em.persist(userRole);
            em.persist(user);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Nested
    @DisplayName("edit project user hours")
    class EditProjectUserHours {

        @Test
        @DisplayName("should correctly plus the old hours with the new")
        void shouldCorrectlyPlusTheOldHoursWithTheNew() throws Exception {
            assertDoesNotThrow(() -> repo.editProjectUserHours(user.getUserName(), project1.getId(), 10));
        }

        @Test
        @DisplayName("should throw exception if given wrong input")
        void shouldThrowExceptionIfGivenWrongInput() throws Exception {
            assertThrows(WebApplicationException.class, () -> repo.editProjectUserHours(null, project1.getId(), 10));
        }

    }

    @Nested
    @DisplayName("complete project user hours")
    class CompleteProjectUserHours {

        @Test
        @DisplayName("should complete a project user hours")
        void shouldCompleteAProjectUserHours() throws Exception {
            ProjectUserHoursDTO projectUserHoursDTO = repo.completeProjectUserHours(user.getUserName(), project1.getId());
            assertTrue(projectUserHoursDTO.isComplete());
        }

        @Test
        @DisplayName("should throw exception given incorrect input")
        void shouldThrowExceptionGivenIncorrectInput() throws Exception {
            assertThrows(
                WebApplicationException.class, () -> repo.completeProjectUserHours(user.getUserName(),
                null)
            );
        }

    }
}