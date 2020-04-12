package test.maksim.video_rental.infrastructure.customer.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import test.maksim.video_rental.domain.customer.model.Customer;
import test.maksim.video_rental.domain.customer.port.CustomerRepositoryPort;
import test.maksim.video_rental.infrastructure.customer.converter.CustomerEntityConverter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepositoryPort {

    private final CustomerRepository repository;
    private final CustomerEntityConverter converter;

    @Override
    public List<Customer> fetchAll() {
        return repository.findAll().stream()
                .map(converter::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> fetchById(Integer id) {
        return repository.findById(id)
                .map(converter::toModel);
    }

    @Override
    public void save(Customer customer) {
        repository.save(converter.toEntity(customer));
    }
}
