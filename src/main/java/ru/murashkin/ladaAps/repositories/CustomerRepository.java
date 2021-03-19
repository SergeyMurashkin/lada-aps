package ru.murashkin.ladaAps.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.murashkin.ladaAps.models.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByUsername(String username);
    Customer findByEmail(String email);
}
