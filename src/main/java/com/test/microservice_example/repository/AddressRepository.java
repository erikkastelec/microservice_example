package com.test.microservice_example.repository;

import com.test.microservice_example.model.Address;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class AddressRepository implements PanacheRepository<Address> {
    public List<Address> findByUserId(Long userId) {
        return find("user.id", userId).list();
    }

    public long countByUserId(Long userId) {
        return count("user.id", userId);
    }

    public Address findDefaultByUserId(Long userId) {
        return find("user.id = ?1 and isDefault = true", userId).firstResult();
    }
}