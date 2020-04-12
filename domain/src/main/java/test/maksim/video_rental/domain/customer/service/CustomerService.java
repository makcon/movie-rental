package test.maksim.video_rental.domain.customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.maksim.video_rental.domain.customer.exception.CustomerNotFoundException;
import test.maksim.video_rental.domain.customer.model.Customer;
import test.maksim.video_rental.domain.customer.port.CustomerRepositoryPort;
import test.maksim.video_rental.domain.movie.service.MovieService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepositoryPort repository;
    private final MovieService movieService;

    public List<Customer> fetchAll() {
        return repository.fetchAll();
    }

    public Customer fetchById(Integer id) {
        return repository.fetchById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public void verifyIfCustomerExists(Integer customerId) {
        fetchById(customerId);
    }

    public void updateBonusPoints(Integer customerId, List<Integer> movieIds) {
        var customer = fetchById(customerId);
        var movies = movieService.fetchByIds(movieIds);

        var bonusPoints = movies.stream()
                .mapToInt(it -> it.getType().getBonusPoints())
                .sum();

        customer.setBonusPoints(customer.getBonusPoints() + bonusPoints);

        repository.save(customer);
    }
}
