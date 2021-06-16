package rest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import entities.project.Project;
import entities.projecthours.ProjectUserHoursRepository;
import entities.user.Role;
import entities.user.User;
import facades.ProjectUserHoursFacade;
import io.restassured.http.ContentType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

class ProjectUserHoursResourceTest extends SetupRestTests {

    @BeforeAll
    static void setUpClass() {
        setupServer();
    }

    @AfterAll
    static void closeTestServer() {
        shutdownServer();
    }

    public static Project project1;
    public static User user;

    @BeforeEach
    void setUp() {
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

    private String login() {
        String json = String.format("{username: \"%s\", password: \"%s\"}", "user", "test");
        return given()
            .contentType("application/json")
            .body(json)
            .when().post("/login")
            .then()
            .extract().path("token");
    }

    @Nested
    @DisplayName("edit project user hours")
    class EditProjectUserHours {

        @Test
        @DisplayName("should edit given the correct input and logged in")
        void shouldEditGivenTheCorrectInputAndLoggedIn() throws Exception {
            String token = login();
            given()
                .contentType(ContentType.JSON)
                .header("x-access-token", token)
                .queryParam("projectId", project1.getId())
                .queryParam("hoursWorked", 15)
                .when()
                .put("/project-user-hours")
                .then()
                .statusCode(200);
        }

    }
}