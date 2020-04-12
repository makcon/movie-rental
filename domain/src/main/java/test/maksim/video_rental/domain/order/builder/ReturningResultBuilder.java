package test.maksim.video_rental.domain.order.builder;

import org.springframework.stereotype.Component;
import test.maksim.video_rental.domain.order.model.Order;
import test.maksim.video_rental.domain.order.result.ReturningResult;
import test.maksim.video_rental.domain.order.result.ReturningResult.OrderData;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReturningResultBuilder {

    public ReturningResult build(List<Order> orders) {
        return ReturningResult.builder()
                .closedOrders(buildOrders(orders))
                .totalOverduePrice(buildTotalOverduePrice(orders))
                .build();
    }

    private int buildTotalOverduePrice(List<Order> orders) {
        return orders.stream()
                .mapToInt(Order::getOverduePrice)
                .sum();
    }

    private List<OrderData> buildOrders(List<Order> orders) {
        return orders.stream()
                .map(this::buildOrder)
                .collect(Collectors.toList());
    }

    private OrderData buildOrder(Order it) {
        return OrderData.builder()
                .orderId(it.getId())
                .movieId(it.getMovieId())
                .overduePrice(it.getOverduePrice())
                .build();
    }
}
