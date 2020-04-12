package test.maksim.video_rental.domain.customer.event;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import test.maksim.video_rental.domain.customer.service.CustomerService;
import test.maksim.video_rental.domain.order.event.OrdersCreatedEvent;
import test.maksim.video_rental.domain.order.model.Order;

import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderEventsListenerShould {

    @InjectMocks
    private OrderEventsListener listener;

    @Mock
    private CustomerService customerService;

    @Test
    void call_customer_service_to_update_bonus_points() {
        // given
        var givenCustomerId = new Random().nextInt();
        var givenMovieId = new Random().nextInt();
        var givenOrder = Order.builder()
                .movieId(givenMovieId)
                .build();
        var givenEvent = OrdersCreatedEvent.builder()
                .customerId(givenCustomerId)
                .orders(List.of(givenOrder))
                .build();

        // when
        listener.onOrdersCreated(givenEvent);

        // then
        verify(customerService).updateBonusPoints(givenCustomerId, List.of(givenMovieId));
    }
}