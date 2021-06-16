package facades;

import static org.junit.jupiter.api.Assertions.*;

import dtos.project.ProjectDTO;
import dtos.project.ProjectsDTO;
import entities.project.Project;
import entities.project.ProjectRepository;
import entities.renameme.RenameMe;
import entities.renameme.RenameMeRepository;
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

    @BeforeEach
    void setUp() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        repo = ProjectFacade.getInstance(emf);
        EntityManager em = emf.createEntityManager();
        try {
            project1 = new Project("project1", "something1");
            project2 = new Project("project2", "something2");
            em.getTransaction().begin(); em.createNamedQuery("Project.deleteAllRows").executeUpdate();
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
}