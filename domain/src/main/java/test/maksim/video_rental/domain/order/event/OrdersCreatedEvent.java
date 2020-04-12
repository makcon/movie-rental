package test.maksim.video_rental.domain.order.event;

import lombok.Builder;
import lombok.Data;
import test.maksim.video_rental.domain.order.model.Order;

import java.util.List;

@Data
@Builder
public class OrdersCreatedEvent {

    private final Integer customerId;
    private final List<Order> orders;
}
