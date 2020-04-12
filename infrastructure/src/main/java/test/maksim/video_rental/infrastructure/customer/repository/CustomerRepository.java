package test.maksim.video_rental.infrastructure.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.maksim.video_rental.infrastructure.customer.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
}
