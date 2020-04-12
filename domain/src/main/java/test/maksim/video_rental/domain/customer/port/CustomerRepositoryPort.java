package test.maksim.video_rental.domain.customer.port;

import test.maksim.video_rental.domain.customer.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepositoryPort {

    List<Customer> fetchAll();

    Optional<Customer> fetchById(Integer id);

    void save(Customer customer);
}
