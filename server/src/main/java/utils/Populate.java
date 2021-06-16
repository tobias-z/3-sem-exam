package utils;

import entities.project.Project;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.WebApplicationException;

public class Populate {

    public static void populate() {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        Project project1 = new Project("project1", "something1");
        Project project2 = new Project("project2", "something2");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Project.deleteAllRows").executeUpdate();
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();
            em.persist(project1);
            em.persist(project2);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new WebApplicationException("Unable to populate");
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        populate();
    }

}
