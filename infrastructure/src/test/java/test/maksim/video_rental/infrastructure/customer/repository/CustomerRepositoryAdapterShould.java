package test.maksim.video_rental.infrastructure.customer.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import test.maksim.video_rental.domain.customer.model.Customer;
import test.maksim.video_rental.infrastructure.customer.converter.CustomerEntityConverter;
import test.maksim.video_rental.infrastructure.customer.entity.CustomerEntity;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerRepositoryAdapterShould {

    private static final Integer GIVEN_CUSTOMER_ID = new Random().nextInt();
    private static final CustomerEntity CUSTOMER_ENTITY = mock(CustomerEntity.class);
    private static final Customer CUSTOMER_MODEL = mock(Customer.class);

    @InjectMocks
    private CustomerRepositoryAdapter adapter;

    @Mock
    private CustomerRepository repository;
    @Mock
    private CustomerEntityConverter converter;

    @Test
    void return_all_found_customers() {
        // given
        when(repository.findAll()).thenReturn(List.of(CUSTOMER_ENTITY));
        when(converter.toModel(any())).thenReturn(CUSTOMER_MODEL);

        // when
        var customers = adapter.fetchAll();

        // then
        assertThat(customers).isEqualTo(List.of(CUSTOMER_MODEL));
        verify(converter).toModel(CUSTOMER_ENTITY);
    }

    @Test
    void return_customer_by_id_if_found() {
        // given
        when(repository.findById(anyInt())).thenReturn(Optional.of(CUSTOMER_ENTITY));
        when(converter.toModel(any())).thenReturn(CUSTOMER_MODEL);

        // when
        var customer = adapter.fetchById(GIVEN_CUSTOMER_ID);

        // then
        assertThat(customer).isEqualTo(Optional.of(CUSTOMER_MODEL));
        verify(repository).findById(GIVEN_CUSTOMER_ID);
        verify(converter).toModel(CUSTOMER_ENTITY);
    }

    @Test
    void return_empty_if_customer_not_found_by_id() {
        // given
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        // when
        var customer = adapter.fetchById(GIVEN_CUSTOMER_ID);

        // then
        assertThat(customer).isEmpty();
        verify(repository).findById(GIVEN_CUSTOMER_ID);
        verify(converter, never()).toModel(any());
    }

    @Test
    void save() {
        // given
        when(converter.toEntity(any())).thenReturn(CUSTOMER_ENTITY);

        // when
        adapter.save(CUSTOMER_MODEL);

        // then
        verify(repository).save(CUSTOMER_ENTITY);
        verify(converter).toEntity(CUSTOMER_MODEL);
    }
}