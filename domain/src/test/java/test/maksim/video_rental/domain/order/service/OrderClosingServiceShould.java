package test.maksim.video_rental.domain.order.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import test.maksim.video_rental.domain.order.calculator.SurchargeCalculator;
import test.maksim.video_rental.domain.order.model.Order;
import test.maksim.video_rental.domain.order.port.OrderRepositoryPort;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderClosingServiceShould {

    @InjectMocks
    private OrderClosingService service;

    @Mock
    private OrderRepositoryPort orderRepository;
    @Mock
    private SurchargeCalculator surchargeCalculator;
    @Mock
    private Clock clock;

    @Test
    void calculate_surcharge_and_close_order() {
        // given
        var givenCustomerId = new Random().nextInt();
        var givenOrderId = new Random().nextInt();
        var storedOrder = Order.builder().build();
        var calculatedSurcharge = 3;
        var expectedClosedAt = Instant.now();
        when(orderRepository.fetchActiveByIdsForCustomer(anyList(), anyInt())).thenReturn(List.of(storedOrder));
        when(surchargeCalculator.calculate(any(), any())).thenReturn(calculatedSurcharge);
        when(orderRepository.save(anyList())).thenReturn(List.of(storedOrder));
        doAnswer(invocation -> expectedClosedAt).when(clock).instant();

        // when
        var orders = service.close(givenCustomerId, List.of(givenOrderId));

        // then
        assertThat(orders).isEqualTo(List.of(storedOrder));
        assertThat(storedOrder.getClosedAt()).isEqualTo(expectedClosedAt);
        assertThat(storedOrder.getOverduePrice()).isEqualTo(calculatedSurcharge);

        verify(orderRepository).fetchActiveByIdsForCustomer(List.of(givenOrderId), givenCustomerId);
        verify(surchargeCalculator).calculate(storedOrder, expectedClosedAt);
        verify(orderRepository).save(List.of(storedOrder));
    }
}