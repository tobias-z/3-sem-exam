package rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;

import dtos.project.AddDeveloperToProjectDTO;
import dtos.project.ProjectDTO;
import entities.project.Project;
import entities.project.ProjectRepository;
import entities.user.Role;
import entities.user.User;
import facades.ProjectFacade;
import io.restassured.http.ContentType;
import java.util.Arrays;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ProjectResourceTest extends SetupRestTests {

    @BeforeAll
    static void setUpClass() {
        setupServer();
    }

    @AfterAll
    static void closeTestServer() {
        shutdownServer();
    }

    public static Project project1, project2;
    User user;

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("ProjectUserHours.deleteAllRows").executeUpdate();
            em.createNamedQuery("Project.deleteAllRows").executeUpdate();
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();

            ////////////////////////////////////////////////////////////////////////////////
            // User

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            user = new User("user", "test");
            user.addRole(userRole);
            User admin = new User("admin", "test");
            admin.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);

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

    private String login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        return given()
            .contentType("application/json")
            .body(json)
            .when().post("/login")
            .then()
            .extract().path("token");
    }

    @Nested
    @DisplayName("get all projects")
    class GetAllProjects {

        @Test
        @DisplayName("should return all projects")
        void shouldReturnAllProjects() throws Exception {
            given()
                .contentType(ContentType.JSON)
                .when()
                .get("/projects")
                .then()
                .statusCode(200);
        }

        @Test
        @DisplayName("should not throw when no projects are found")
        void shouldNotThrowWhenNoProjectsAreFound() throws Exception {
            dropAllProjects();
            given()
                .contentType(ContentType.JSON)
                .when()
                .get("/projects")
                .then()
                .statusCode(200);
        }

    }

    @Nested
    @DisplayName("create project")
    class CreateProject {

        @Test
        @DisplayName("admin should be able to create a project")
        void adminShouldBeAbleToCreateAProject() throws Exception {
            String token = login("admin", "test");
            ProjectDTO projectDTO = new ProjectDTO("Create", "Me");
            given()
                .contentType(ContentType.JSON)
                .body(projectDTO)
                .header("x-access-token", token)
                .when()
                .post("/projects")
                .then()
                .statusCode(200);
        }

        @Test
        @DisplayName("should return an error if incorrect json was provided")
        void shouldReturnAnErrorIfIncorrectJsonWasProvided() throws Exception {
            String token = login("admin", "test");
            ProjectDTO projectDTO = new ProjectDTO("Create", null);
            given()
                .contentType(ContentType.JSON)
                .body(projectDTO)
                .header("x-access-token", token)
                .when()
                .post("/projects")
                .then()
                .statusCode(400);
        }

        @Test
        @DisplayName("user cannot create project")
        void userCannotCreateProject() throws Exception {
            String token = login("user", "test");
            ProjectDTO projectDTO = new ProjectDTO("Create", "Me");
            given()
                .contentType(ContentType.JSON)
                .body(projectDTO)
                .header("x-access-token", token)
                .when()
                .post("/projects")
                .then()
                .statusCode(401);
        }
    }

    @Nested
    @DisplayName("add developer to project")
    class AddDeveloperToProject {

        @Test
        @DisplayName("should add a developer to the project")
        void shouldAddADeveloperToTheProject() throws Exception {
            String token = login("admin", "test");
            AddDeveloperToProjectDTO addDeveloperToProjectDTO = new AddDeveloperToProjectDTO("Insane",
                "DO it quick");
            given()
                .contentType(ContentType.JSON)
                .header("x-access-token", token)
                .queryParam("username", user.getUserName())
                .queryParam("projectId", project2.getId())
                .body(addDeveloperToProjectDTO)
                .when()
                .put("/projects")
                .then()
                .statusCode(200);
        }

        @Test
        @DisplayName("should return error when given incorrect username")
        void shouldReturnErrorWhenGivenIncorrectUsername() throws Exception {
            String token = login("admin", "test");
            AddDeveloperToProjectDTO addDeveloperToProjectDTO = new AddDeveloperToProjectDTO("Insane",
                "DO it quick");
            given()
                .contentType(ContentType.JSON)
                .header("x-access-token", token)
                .queryParam("username", "dsadadadadasda")
                .queryParam("projectId", project2.getId())
                .body(addDeveloperToProjectDTO)
                .when()
                .put("/projects")
                .then()
                .statusCode(400);
        }

    }

    @Nested
    @DisplayName("get all developers projects")
    class GetAllDevelopersProjects {

        ProjectRepository repo = ProjectFacade.getInstance(emf);

        @BeforeEach
        void setUp() {
            AddDeveloperToProjectDTO addDeveloperToProjectDTO = new AddDeveloperToProjectDTO("Something",
                "Do it");
            repo.addDeveloperToProject(user.getUserName(), project2.getId(), addDeveloperToProjectDTO);
        }

        @Test
        @DisplayName("getAllDevelopersProjects should return the developers projects")
        void getAllDevelopersProjectsShouldReturnTheDevelopersProjects() throws Exception {
            String token = login("user", "test");
            given()
                .contentType(ContentType.JSON)
                .pathParam("username", user.getUserName())
                .header("x-access-token", token)
                .when()
                .get("/projects/{username}")
                .then()
                .statusCode(200);
        }

    }


}