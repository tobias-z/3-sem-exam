package facades;

import static org.junit.jupiter.api.Assertions.*;

import dtos.project.ProjectDTO;
import dtos.project.ProjectsDTO;
import entities.project.Project;
import entities.project.ProjectRepository;
import entities.renameme.RenameMe;
import entities.renameme.RenameMeRepository;
import entities.user.Role;
import entities.user.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.ws.rs.WebApplicationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

class ProjectFacadeTest {

    private static EntityManagerFactory emf;
    private static ProjectRepository repo;

    public static Project project1, project2;
    public static User user;

    @BeforeEach
    void setUp() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        repo = ProjectFacade.getInstance(emf);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Project.deleteAllRows").executeUpdate();
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();

            ////////////////////////////////////////////////////////////////////////////////
            // User

            Role userRole = new Role("user");
            user = new User("user", "test");
            user.addRole(userRole);
            em.persist(userRole);
            em.persist(user);

            ////////////////////////////////////////////////////////////////////////////////
            // Project

            project1 = new Project("project1", "something1");
            project2 = new Project("project2", "something2");
            em.persist(project1);
            em.persist(project2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    private void dropAllProjects() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Project.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Nested
    @DisplayName("get all projects")
    class GetAllProjects {

        @Test
        @DisplayName("should return all the projects")
        void shouldReturnAllTheProjects() throws Exception {
            ProjectsDTO projects = repo.getAllProjects();
            assertFalse(projects.getProjects().isEmpty());
            assertEquals(2, projects.getProjects().size());
        }

        @Test
        @DisplayName("should not throw exeption if no projects were found")
        void shouldNotThrowExeptionIfNoProjectsWereFound() throws Exception {
            dropAllProjects();
            ProjectsDTO projects = repo.getAllProjects();
            assertTrue(projects.getProjects().isEmpty());
        }

    }

    @Nested
    @DisplayName("create project")
    class CreateProject {

        @Test
        @DisplayName("should create a project given a correct projectDTO")
        void shouldCreateAProjectGivenACorrectProjectDto() throws Exception {
            ProjectDTO projectDTO = new ProjectDTO("Create", "Me");
            ProjectDTO createdProject = repo.createProject(projectDTO);
            assertEquals(projectDTO.getDescription(), createdProject.getDescription());
            assertEquals(projectDTO.getName(), createdProject.getName());
        }

        @Test
        @DisplayName("should throw exception given an incorrect projectDTO")
        void shouldThrowExceptionGivenAnIncorrectProjectDto() throws Exception {
            ProjectDTO projectDTO = new ProjectDTO(null, "hello");
            assertThrows(WebApplicationException.class, () -> repo.createProject(projectDTO));
        }

    }

    @Nested
    @DisplayName("add developer to project")
    class AddDeveloperToProject {

        @Test
        @DisplayName("should add a developer to project")
        void shouldAddADeveloperToProject() throws Exception {
            ProjectDTO projectDTO = repo.addDeveloperToProject(user.getUserName(), project1.getId());
            assertEquals(1, projectDTO.getDevelopers().size());
        }

        @Test
        @DisplayName("should throw exception if unknown user")
        void shouldThrowExceptionIfUnknownUser() throws Exception {
            assertThrows(
                WebApplicationException.class,
                () -> repo.addDeveloperToProject(null, project1.getId())
            );
        }

        @Test
        @DisplayName("should throw exception if project does not exist")
        void shouldThrowExceptionIfProjectDoesNotExist() throws Exception {
            assertThrows(
                WebApplicationException.class,
                () -> repo.addDeveloperToProject(user.getUserName(), null)
            );
        }

    }
}