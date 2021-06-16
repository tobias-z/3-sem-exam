package entities.user;

import javax.persistence.EntityManager;

@FunctionalInterface
public interface UserAction<T> {

    T commit(User user, EntityManager entityManager);

}
