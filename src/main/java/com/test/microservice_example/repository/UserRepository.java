package com.test.microservice_example.repository;

import com.test.microservice_example.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;


@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    @Inject
    EntityManager entityManager;

    public User findUserByIdentifier(String identifier) {
        String queryStr = "SELECT u FROM User u WHERE u.id1 = :identifier OR u.id2 = :identifier OR u.id3 = :identifier";
        TypedQuery<User> query = entityManager.createQuery(queryStr, User.class);
        query.setParameter("identifier", identifier);
        return query.getResultList().stream().findFirst().orElse(null);
    }
}