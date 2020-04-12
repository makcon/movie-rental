package test.maksim.video_rental.domain.customer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import test.maksim.video_rental.domain.customer.exception.CustomerNotFoundException;
import test.maksim.video_rental.domain.customer.model.Customer;
import test.maksim.video_rental.domain.customer.port.CustomerRepositoryPort;
import test.maksim.video_rental.domain.movie.model.Movie;
import test.maksim.video_rental.domain.movie.model.MovieType;
import test.maksim.video_rental.domain.movie.service.MovieService;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceShould {

    private static final Integer GIVEN_CUSTOMER_ID = new Random().nextInt();
    private static final Customer STORED_CUSTOMER = mock(Customer.class);

    @InjectMocks
    private CustomerService service;

    @Mock
    private CustomerRepositoryPort repository;
    @Mock
    private MovieService movieService;

    @Test
    void return_all_stored_customers() {
        // given
        when(repository.fetchAll()).thenReturn(List.of(STORED_CUSTOMER));

        // when
        var customer = service.fetchAll();

        // then
        assertThat(customer).isEqualTo(List.of(STORED_CUSTOMER));
    }

    @Test
    void return_customer_if_found() {
        // given
        when(repository.fetchById(anyInt())).thenReturn(Optional.of(STORED_CUSTOMER));

        // when
        var customer = service.fetchById(GIVEN_CUSTOMER_ID);

        // then
        assertThat(customer).isEqualTo(STORED_CUSTOMER);
    }

    @Test
    void throw_not_found_exception_if_requested_customer_not_found() {
        // given
        when(repository.fetchById(anyInt())).thenReturn(Optional.empty());

        // when
        assertThrows(
                CustomerNotFoundException.class,
                () -> service.fetchById(GIVEN_CUSTOMER_ID)
        );
    }

    @Test
    void throw_not_found_exception_if_customer_not_found_to_verify() {
        // given
        when(repository.fetchById(anyInt())).thenReturn(Optional.empty());

        // when
        assertThrows(
                CustomerNotFoundException.class,
                () -> service.verifyIfCustomerExists(GIVEN_CUSTOMER_ID)
        );
    }

    @Test
    void not_throw_exception_if_customer_found_to_verify() {
        // given
        when(repository.fetchById(anyInt())).thenReturn(Optional.of(STORED_CUSTOMER));

        // when
        assertDoesNotThrow(() -> service.verifyIfCustomerExists(GIVEN_CUSTOMER_ID));
    }

    @Test
    void updateBonusPoints() {
        // given
        var movie1Points = 2;
        var movie2Points = 3;
        var customerPoints = 1;
        var storedCustomer = Customer.builder()
                .bonusPoints(customerPoints)
                .build();
        var givenMovieIds = List.of(new Random().nextInt(), new Random().nextInt());
        when(repository.fetchById(anyInt())).thenReturn(Optional.of(storedCustomer));
        when(movieService.fetchByIds(anyList()))
                .thenReturn(List.of(createMovie(movie1Points), createMovie(movie2Points)));

        // when
        service.updateBonusPoints(GIVEN_CUSTOMER_ID, givenMovieIds);

        // then
        assertThat(storedCustomer.getBonusPoints()).isEqualTo(customerPoints + movie1Points + movie2Points);
        verify(movieService).fetchByIds(givenMovieIds);
        verify(repository).save(storedCustomer);
    }

    private Movie createMovie(int bonusPoints) {
        return Movie.builder()
                .type(MovieType.builder().bonusPoints(bonusPoints).build())
                .build();
    }
}