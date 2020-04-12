package test.maksim.video_rental.infrastructure.order.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import test.maksim.video_rental.domain.order.model.Order;
import test.maksim.video_rental.infrastructure.order.converter.OrderEntityConverter;
import test.maksim.video_rental.infrastructure.order.entity.OrderEntity;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderRepositoryAdapterShould {

    private static final OrderEntity ORDER_ENTITY = mock(OrderEntity.class);
    private static final Order ORDER_MODEL = mock(Order.class);

    @InjectMocks
    private OrderRepositoryAdapter adapter;

    @Mock
    private OrderRepository repository;
    @Mock
    private OrderEntityConverter converter;

    @Test
    void save() {
        // given
        var expectedSavedEntity = mock(OrderEntity.class);
        var expectedConvertedModel = mock(Order.class);
        when(converter.toEntity(any())).thenReturn(ORDER_ENTITY);
        when(repository.saveAll(anyList())).thenReturn(List.of(expectedSavedEntity));
        when(converter.toModel(any())).thenReturn(expectedConvertedModel);

        // when
        var orders = adapter.save(List.of(ORDER_MODEL));

        // then
        assertThat(orders).isEqualTo(List.of(expectedConvertedModel));
        verify(converter).toEntity(ORDER_MODEL);
        verify(repository).saveAll(List.of(ORDER_ENTITY));
        verify(converter).toModel(expectedSavedEntity);
    }

    @Test
    void fetchActiveByIdsForCustomer() {
        // given
        var givenCustomerId = new Random().nextInt();
        var givenOrderIds = List.of(new Random().nextInt());
        when(repository.findAllByIdInAndCustomerIdAndClosedAtIsNull(anyList(), any()))
                .thenReturn(List.of(ORDER_ENTITY));
        when(converter.toModel(any())).thenReturn(ORDER_MODEL);

        // when
        var orders = adapter.fetchActiveByIdsForCustomer(givenOrderIds, givenCustomerId);

        // then
        assertThat(orders).isEqualTo(List.of(ORDER_MODEL));
        verify(repository).findAllByIdInAndCustomerIdAndClosedAtIsNull(givenOrderIds, givenCustomerId);
        verify(converter).toModel(ORDER_ENTITY);
    }
}