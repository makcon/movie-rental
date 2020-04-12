package test.maksim.video_rental.domain.order.builder;

import org.springframework.stereotype.Component;
import test.maksim.video_rental.domain.order.model.Order;
import test.maksim.video_rental.domain.order.result.RentingResult;
import test.maksim.video_rental.domain.order.result.RentingResult.OrderData;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentingResultBuilder {

    public RentingResult build(List<Order> orders) {
        return RentingResult.builder()
                .totalPrice(buildTotalPrice(orders))
                .createdOrders(buildOrders(orders))
                .build();
    }

    private int buildTotalPrice(List<Order> orders) {
        return orders.stream()
                .mapToInt(Order::getPrice)
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
                .price(it.getPrice())
                .movieId(it.getMovieId())
                .expiresAt(it.getExpiresAt())
                .build();
    }
}
