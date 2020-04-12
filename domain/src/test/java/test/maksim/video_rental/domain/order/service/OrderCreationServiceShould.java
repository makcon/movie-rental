package test.maksim.video_rental.domain.order.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import test.maksim.video_rental.domain.movie.model.Movie;
import test.maksim.video_rental.domain.movie.service.MovieService;
import test.maksim.video_rental.domain.order.calculator.RentingDataCalculator;
import test.maksim.video_rental.domain.order.event.OrdersCreatedEvent;
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
class OrderCreationServiceShould {
    
    @InjectMocks
    private OrderCreationService service;

    @Mock
    private MovieService movieService;
    @Mock
    private RentingDataCalculator rentingDataCalculator;
    @Mock
    private OrderRepositoryPort orderRepository;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @Mock
    private Clock clock;

    @Test
    void create_order_save_it_and_send_event() {
        // given
        var givenCustomerId = new Random().nextInt();
        var givenMovieId = new Random().nextInt();
        var givenRentingDays = new Random().nextInt();
        var storedMovie = Movie.builder()
                .id(givenMovieId)
                .build();
        var calculatedPrice = new Random().nextInt();
        var expectedCreatedAt = Instant.now();
        var calculatedExpiresAt = expectedCreatedAt.plusSeconds(1);
        when(rentingDataCalculator.calculatePrice(any(), anyInt())).thenReturn(calculatedPrice);
        when(rentingDataCalculator.calculateExpiresAt(any(), any(), anyInt())).thenReturn(calculatedExpiresAt);
        when(movieService.fetchByIds(anyList())).thenReturn(List.of(storedMovie));
        when(orderRepository.save(anyList())).thenReturn(List.of(mock(Order.class)));
        doAnswer(invocation -> expectedCreatedAt).when(clock).instant();

        // when
        var orders = service.create(givenCustomerId, List.of(givenMovieId), givenRentingDays);

        // then
        ArgumentCaptor<List<Order>> ordersCapture = ArgumentCaptor.forClass(List.class);
        verify(orderRepository).save(ordersCapture.capture());
        assertThat(ordersCapture.getValue()).hasSize(1);
        var createdOrder = ordersCapture.getValue().get(0);
        assertThat(createdOrder.getId()).isNull();
        assertThat(createdOrder.getCreatedAt()).isEqualTo(expectedCreatedAt);
        assertThat(createdOrder.getCustomerId()).isEqualTo(givenCustomerId);
        assertThat(createdOrder.getMovieId()).isEqualTo(givenMovieId);
        assertThat(createdOrder.getPrice()).isEqualTo(calculatedPrice);
        assertThat(createdOrder.getExpiresAt()).isEqualTo(calculatedExpiresAt);
        assertThat(createdOrder.getOverduePrice()).isZero();
        assertThat(createdOrder.getClosedAt()).isNull();

        assertThat(orders).isNotEqualTo(ordersCapture.getValue());

        verify(rentingDataCalculator).calculatePrice(storedMovie, givenRentingDays);
        verify(rentingDataCalculator).calculateExpiresAt(storedMovie, expectedCreatedAt, givenRentingDays);
        verify(eventPublisher).publishEvent(
                OrdersCreatedEvent.builder()
                        .customerId(givenCustomerId)
                        .orders(orders)
                        .build()
        );
    }
}