package test.maksim.video_rental.domain.customer.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import test.maksim.video_rental.domain.customer.service.CustomerService;
import test.maksim.video_rental.domain.order.event.OrdersCreatedEvent;
import test.maksim.video_rental.domain.order.model.Order;

import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventsListener {

    private final CustomerService customerService;

    @EventListener
    public void onOrdersCreated(OrdersCreatedEvent event) {
        log.info("Received event: {}", event);
        customerService.updateBonusPoints(
                event.getCustomerId(),
                event.getOrders().stream()
                        .map(Order::getMovieId)
                        .collect(Collectors.toList())
        );
    }
}
