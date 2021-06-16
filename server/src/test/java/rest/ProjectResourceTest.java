package rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;

import dtos.project.ProjectDTO;
import entities.project.Project;
import entities.user.Role;
import entities.user.User;
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

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Project.deleteAllRows").executeUpdate();
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();

            ////////////////////////////////////////////////////////////////////////////////
            // User

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            User user = new User("user", "test");
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

}